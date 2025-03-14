package com.bannrx.common.service;

import com.bannrx.common.dtos.SignUpRequest;
import com.bannrx.common.dtos.UserDto;
import com.bannrx.common.persistence.entities.User;
import com.bannrx.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ObjectMapperUtils;
import java.util.Optional;

@Service
@Loggable
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(SignUpRequest request) {
        var retVal = new User();
        retVal.setName(request.getName());
        retVal.setPhoneNo(request.getPhoneNo());
        return userRepository.save(retVal);
    }

    public boolean isExistingUser(String phoneNo){
        return existingContactNo(phoneNo);
    }

    public UserDto update(UserDto userDto) throws ServerException, InvalidInputException {
        User user = fetchById(userDto.getId());
        ObjectMapperUtils.map(userDto,user);
        user = userRepository.save(user);
        return ObjectMapperUtils.map(user,UserDto.class);
    }

    public void delete(String phoneNo) {
        var user = userRepository.findByPhoneNo(phoneNo);
        userRepository.delete(user.get());
    }

    public User fetchById(String id) throws InvalidInputException {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException(
                        String.format("User not found with Id %s", id)));
    }

    public User findByPhoneNo(String phoneNo) throws InvalidInputException {
        return userRepository.findByPhoneNo(phoneNo)
                .orElseThrow(() -> new InvalidInputException(
                        String.format("User not found with phone no %s", phoneNo)
                ));
    }

    public boolean existingContactNo(String phoneNo){
        Optional<User> userMayBe = userRepository.findByPhoneNo(phoneNo);
        return userMayBe.isPresent();
    }

    public boolean isAlreadyRegister(SignUpRequest request) {
        return existingContactNo(request.getPhoneNo());
    }

    /**
     * Fetches the user for the header context
     * This is yet to be implemented
     *
     * @return user
     */
    public User getLoggedInUser(){
        var retVal = new User();
        retVal.setId("default");
        return retVal;
    }

}
