package com.bannrx.common.service;

import com.bannrx.common.dtos.*;
import com.bannrx.common.dtos.user.BDAUserExcelDto;
import com.bannrx.common.dtos.user.UserBasicDetailsDto;
import com.bannrx.common.dtos.user.UserDto;
import com.bannrx.common.dtos.requests.SignUpRequest;
import com.bannrx.common.dtos.responses.PageableResponse;
import com.bannrx.common.mappers.UserDetailsMapper;
import com.bannrx.common.persistence.entities.User;
import com.bannrx.common.repository.UserRepository;
import com.bannrx.common.searchCriteria.UserSearchCriteria;
import com.bannrx.common.specifications.UserSpecification;
import com.bannrx.common.utilities.SecurityUtils;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rklab.utility.annotations.Loggable;
import com.bannrx.common.dtos.responses.BDAResponse;
import rklab.utility.dto.InvalidExcelRecordDto;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ExcelUtils;
import rklab.utility.utilities.IdGenerator;
import rklab.utility.utilities.ObjectMapperUtils;
import rklab.utility.utilities.PageableUtils;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static com.bannrx.common.enums.UserRole.ROLE_BDA;


import java.util.Objects;
import java.util.Optional;



@Service
@Loggable
@NoArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private BusinessService businessService;
    @Autowired private BankDetailsService bankDetailsService;
    @Autowired private AddressService addressService;
    @Autowired private UserProfileService profileService;

    private static final String PASSWORD = "bannrx123";

    /**
     * Sign up user dto. As this is a free request, the security context will not have logged-in user.
     * So, here, we need to explicitly define the createdBy and modifiedBy.
     * This method not to be used for request with security context.
     *
     * @param request the request
     * @return the user dto
     * @throws ServerException the server exception
     */
    @Transactional
    public UserDto signUp(SignUpRequest request) throws ServerException {
        var user = toEntity(request);
        user.setId(user.getPrefix().concat(IdGenerator.generateId()));
        setUpCreatedByAndModifiedBy(user);
        user.appendRole(request.getRole());
        user = userRepository.save(user);
        return toDto(user);
    }

    private UserDto toDto(User user) throws ServerException {
        var userDto = ObjectMapperUtils.map(user, UserDto.class);
        var profile = user.getUserProfile();
        if(Objects.nonNull(profile)) {
            var bankDtoSet = bankDetailsService.toDto(profile.getBankDetails());
            var addressDtoSet = addressService.toDto(profile.getAddresses());
            var businessDto = businessService.toDto(profile.getBusiness());
            userDto.setAddressDtoSet(addressDtoSet);
            userDto.setBankDetailsDtoSet(bankDtoSet);
            userDto.setBusinessDto(businessDto);
        }
        return userDto;
    }

    private User toEntity(SignUpRequest request) throws ServerException {
        var retVal = ObjectMapperUtils.map(request, User.class);
        retVal.createProfile();
        var profile = retVal.getUserProfile();
        var bankDetails = bankDetailsService.toEntitySet(request.getBankDetailsDtoSet());
        var addresses = addressService.toEntitySet(request.getAddressDtoSet());
        var business = businessService.toEntity(request.getBusinessDto());
        profile.setBankDetails(bankDetails);
        bankDetails.forEach(profile::appendBankDetail);
        addresses.forEach(profile::appendAddress);
        profile.setBusiness(business);
        return retVal;
    }

    /**
     * The below method is used to set createdBy and modified by for request
     * where the logged-in user is not available or free requests or user sign up
     * request where the token will be not available.
     *
     * @param user signing up user
     */
    private void setUpCreatedByAndModifiedBy(User user){
        var id = user.getId();
        user.setCreatedBy(id);
        user.setModifiedBy(id);
        var profile = user.getUserProfile();
        profile.setCreatedBy(id);
        profile.setModifiedBy(id);
        if (CollectionUtils.isNotEmpty(profile.getAddresses())){
            profile.getAddresses().forEach(address -> {
                address.setCreatedBy(id);
                address.setModifiedBy(id);
            });
        }
        if (CollectionUtils.isNotEmpty(profile.getBankDetails())){
            profile.getBankDetails().forEach(bankDetails -> {
                bankDetails.setCreatedBy(id);
                bankDetails.setModifiedBy(id);
            });
        }
        if (Objects.nonNull(profile.getBusiness())){
            var business = profile.getBusiness();
            business.setCreatedBy(id);
            business.setModifiedBy(id);
        }
    }

    public UserDto updateBasicDetails(UserBasicDetailsDto userDto) throws ServerException, InvalidInputException {
        User user = fetchById(userDto.getId());
        validateUpdateRequest(userDto, user);
        ObjectMapperUtils.map(userDto,user);
        user = userRepository.save(user);
        return toDto(user);
    }

    private void validateUpdateRequest(final UserBasicDetailsDto userDto, final User user) throws InvalidInputException {
        if (
                StringUtils.isBlank(userDto.getName()) &&
                        StringUtils.isBlank(userDto.getPassword()) &&
                        StringUtils.isBlank(userDto.getPhoneNo()) &&
                        StringUtils.isBlank(userDto.getEmail())
        ){
            throw new InvalidInputException("No changes found.");
        }
        validateUpdatePhoneNo(userDto, user);
        validateUpdateEmail(userDto, user);
    }

    private void validateUpdatePhoneNo(final UserBasicDetailsDto userDto, final User user) throws InvalidInputException {
        if (StringUtils.isNotBlank(userDto.getPhoneNo())){
            if (StringUtils.equals(userDto.getPhoneNo(), user.getPhoneNo())){
                throw new InvalidInputException("Phone no didn't changed. Please provide updated details only.");
            }
            var exists = existingContactNo(userDto.getPhoneNo());
            if (exists){
                throw new InvalidInputException("User already exists with the phone no "+userDto.getPhoneNo());
            }
        }
    }

    private void validateUpdateEmail(final UserBasicDetailsDto userDto, final User user) throws InvalidInputException {
        if (StringUtils.isNotBlank(userDto.getEmail())){
            if (StringUtils.equals(userDto.getEmail(), user.getEmail())){
                throw new InvalidInputException("Email didn't changed. Please provide updated details only.");
            }
            var exists = existingEmail(userDto.getEmail());
            if (exists){
                throw new InvalidInputException("User already exists with the email "+userDto.getEmail());
            }
        }
    }

    public void delete(String phoneNo) {
        var user = userRepository.findByPhoneNo(phoneNo);
        userRepository.delete(user.get());
    }

    /**
     * Fetch pageable response.
     *
     * @param searchCriteria the user search criteria
     * @return the pageable response
     */
    public PageableResponse<UserDto> fetch(UserSearchCriteria searchCriteria){
        var pageable = PageableUtils.createPageable(searchCriteria);
        searchCriteria.setLoggedInUserId(getLoggedInUserId());
        var userPage = userRepository.findAll(
                UserSpecification.buildSearchCriteria(searchCriteria),
                pageable
        );
        var userList = userPage.getContent().stream()
                .map(this::getUserDto)
                .filter(Objects::nonNull)
                .toList();
        return new PageableResponse<>(userList, searchCriteria);
    }



    @Transactional
    public BDAResponse createBDAUser(MultipartFile file, int sheetNo) throws IOException {
        var excelParseData = ExcelUtils.validateAndParseToDto(file.getInputStream(), BDAUserExcelDto.class, sheetNo);
        var userIdSet = new LinkedHashSet<String>();
        for(var bdaUser : excelParseData.getParsedDtoSet()){
            var user = UserDetailsMapper.INSTANCE.toEntity(bdaUser, PASSWORD, ROLE_BDA);
            var bankDetails = Set.of(UserDetailsMapper.INSTANCE.toEntity(bdaUser, true));
            var addresses = Set.of(UserDetailsMapper.INSTANCE.toEntity(bdaUser));
            var profile = user.getUserProfile();
            bankDetails.forEach(profile::appendBankDetail);
            addresses.forEach(profile::appendAddress);
            user = userRepository.save(user);
            userIdSet.add(user.getId());
        }
        var errorCasesSet = excelParseData.getInvalidExcelRecordDtoList().stream()
                .map(InvalidExcelRecordDto::toString)
                .collect(Collectors.toSet());
        return new BDAResponse(userIdSet, errorCasesSet);
    }

    /**
     * below method ignores the error.
     *
     */
    private UserDto getUserDto(User user){
        try{
            return toDto(user);
        } catch (ServerException e) {
            return null;
        }
    }


    /**
     * Below goes all the database interaction logics
     */

    /**
     * Fetch by id user.
     *
     * @param id the id
     * @return the user
     * @throws InvalidInputException the invalid input exception
     */
    public User fetchById(String id) throws InvalidInputException {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException(
                        String.format("User not found with Id %s", id)));
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return Optional user
     */
    public Optional<User> getById(String id){
        return userRepository.findById(id);
    }

    public User fetchByPhoneNo(String phoneNo) throws InvalidInputException {
        return userRepository.findByPhoneNo(phoneNo)
                .orElseThrow(() -> new InvalidInputException(
                        String.format("User not found with phone no %s", phoneNo)
                ));
    }

    public User fetchByEmail(String email) throws InvalidInputException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidInputException(
                        String.format("User not found with email %s", email)
                ));
    }

    /**
     * Fetch by username user.
     *
     * @param username the username
     * @return the user
     * @throws InvalidInputException the invalid input exception
     */
    public User fetchByUsername(final String username) throws InvalidInputException {
        return userRepository.findByEmailOrPhoneNo(username, username)
                .orElseThrow(() -> new InvalidInputException(
                        String.format("User not found with username as %s", username))
                );
    }

    /**
     * Existing contact no boolean.
     *
     * @param phoneNo the phone no
     * @return the boolean
     */
    public boolean existingContactNo(String phoneNo){
        Optional<User> userMayBe = userRepository.findByPhoneNo(phoneNo);
        return userMayBe.isPresent();
    }

    /**
     * Existing email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean existingEmail(String email){
        Optional<User> userMayBe = userRepository.findByEmail(email);
        return userMayBe.isPresent();
    }

    public boolean isAlreadyRegister(SignUpRequest request) {
        return existsByEmailOrPhoneNo(request.getEmail(), request.getPhoneNo());
    }

    /**
     * Exists by email or phone no.
     *
     * @param email the email
     * @param phone the phone
     * @return the boolean
     */
    public boolean existsByEmailOrPhoneNo(final String email, final String phone){
        return userRepository.existsByEmailOrPhoneNo(email, phone);
    }

    /**
     * Fetches the user for the header context
     *
     * @return user
     */
    public SecurityUserDto fetchLoggedInUser() throws InvalidInputException {
        var userMayBe = Optional.ofNullable(SecurityUtils.getLoggedInUser());
        if (userMayBe.isPresent() &&
            userMayBe.get() instanceof SecurityUserDto user){
            return user;
        }
        throw new InvalidInputException("Error while getting User from security context.");
    }

    private String getLoggedInUserId(){
        var userMayBe = Optional.ofNullable(SecurityUtils.getLoggedInUser());
        if (userMayBe.isPresent() &&
                userMayBe.get() instanceof SecurityUserDto user){
            return user.getId();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userMayBe = getById(username);
        if (userMayBe.isPresent()){
            return new SecurityUserDto(userMayBe.get());
        }
        throw new UsernameNotFoundException("User not found");
    }

    /**
     * Save or update user.
     *
     * @param user the user
     * @return the user
     */
    public User saveOrUpdate(User user){
        return userRepository.save(user);
    }

    public String deleteUserProfile(String userId) throws InvalidInputException {
        var user = fetchById(userId);
        var profile = user.getUserProfile();
        user.setUserProfile(null);
        return profileService.delete(profile);
    }
}
