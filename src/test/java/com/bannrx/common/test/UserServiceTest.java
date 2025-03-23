package com.bannrx.common.test;

import com.bannrx.common.dtos.*;
import com.bannrx.common.enums.UserRole;
import com.bannrx.common.persistence.entities.Address;
import com.bannrx.common.persistence.entities.BankDetails;
import com.bannrx.common.persistence.entities.Business;
import com.bannrx.common.persistence.entities.User;
import com.bannrx.common.repository.UserRepository;
import com.bannrx.common.service.AddressService;
import com.bannrx.common.service.BankDetailsService;
import com.bannrx.common.service.BusinessService;
import com.bannrx.common.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rklab.utility.expectations.ServerException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressService addressService;
    @Mock
    private BankDetailsService bankDetailsService;
    @Mock
    private BusinessService businessService;
    @InjectMocks
    private UserService userService;

    private SignUpRequest validRequest;
    private User savedUser;
    private Address savedAddress;
    private BankDetails savedBankDetail;
    private Business savedBusiness;

    @BeforeEach
    public void setUp(){
        validRequest = new SignUpRequest();
        validRequest.setName("Saurav");
        validRequest.setPhoneNo("1234567890");
        validRequest.setEmail("saurav@gmail.com");
        validRequest.setPassword("password@123");
        validRequest.setRole(UserRole.ROLE_CAMPAIGNER);
        validRequest.setAddressDtoList(List.of(new AddressDto()));
        validRequest.setBankDetailsDtoList(List.of(new BankDetailsDto()));
        validRequest.setBusinessDto(new BusinessDto());

        savedUser = new User();
        savedUser.setId("UR20250322VIcPL65n24");
        savedUser.setEmail("saurav@gmail.com");

        savedAddress = new Address();
        savedAddress.setId("AD20250322CecrWvsy24");

        savedBankDetail = new BankDetails();
        savedBankDetail.setId("BD20250322JparUDNA24");

        savedBusiness = new Business();
        savedBusiness.setId("BU20250322l2OOKfgw24");
    }

    @Test
    public void testCreateUser_Success() throws ServerException {
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(savedUser);
        when(addressService.save(anyList(),any(User.class))).thenReturn(Set.of(savedAddress));
        when(bankDetailsService.save(anyList(), any(User.class))).thenReturn(Set.of(savedBankDetail));
        when(businessService.save(any(BusinessDto.class))).thenReturn(savedBusiness);
        UserDto userDto = userService.createUser(validRequest);

        assertNotNull(userDto);
        assertEquals("saurav@gmail.com", userDto.getEmail());
        assertEquals(1, userDto.getAddressDtoList().size());
        assertEquals(1, userDto.getBankDetailsDtoList().size());
        assertNotNull(userDto.getBusinessDto());

        verify(userRepository, times(1)).save(any(User.class));
        verify(addressService, times(1)).save(anyList(), any(User.class));
        verify(bankDetailsService, times(1)).save(anyList(), any(User.class));
        verify(businessService, times(1)).save(any(BusinessDto.class));
    }

    @Test
    public void testCreateUser_MissingRequiredFields() {
        SignUpRequest invalidRequest = new SignUpRequest();
        invalidRequest.setEmail("saurav@gmail.com");
        assertThrows(ServerException.class, () -> userService.createUser(invalidRequest));
    }

    @Test
    public void testCreateUser_InvalidEmailFormat() {
        validRequest.setEmail("invalid-email");
        assertThrows(ServerException.class, () -> userService.createUser(validRequest));
    }

    @Test
    public void testCreateUser_NullAddressList() {
        validRequest.setAddressDtoList(null);
        assertThrows(ServerException.class, () -> userService.createUser(validRequest));
    }

    @Test
    public void testCreateUser_EmptyBankDetailsList() {
        validRequest.setBankDetailsDtoList(Collections.emptyList());
        assertThrows(ServerException.class, () -> userService.createUser(validRequest));
    }

    @Test
    public void testCreateUser_DatabaseError() {
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> userService.createUser(validRequest));
    }

}
