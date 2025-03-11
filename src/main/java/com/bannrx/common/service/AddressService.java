package com.bannrx.common.service;

import com.bannrx.common.dtos.AddressDto;
import com.bannrx.common.entities.Address;
import com.bannrx.common.entities.User;
import com.bannrx.common.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ObjectMapperUtils;



@Service
@AllArgsConstructor
public class AddressService {

    private static final String DELETE_MSG = "Address(s) has been deleted";

    private final AddressRepository addressRepository;
    private final UserService userService;


    public AddressDto addAddress(AddressDto addressDto) throws ServerException, InvalidInputException {
        Address address = new Address();
        User user = userService.findByPhoneNo(addressDto.getPhoneNo());
        ObjectMapperUtils.map(addressDto,address);
        user.setAddress(address);
        addressRepository.save(address);
        return ObjectMapperUtils.map(address, AddressDto.class);
    }

    public String delete(String id) throws ServerException {

        var addressMaybe = addressRepository.findById(id);
        addressRepository.delete(addressMaybe.get());
        return DELETE_MSG;
    }


    public void canAddressBeAdded(User user, AddressDto addressDto) throws InvalidInputException {
        if (user.getAddress() != null){
            throw new InvalidInputException("You can add only 1 addresses to add new address delete existing address.");
        }
    }


    public boolean isSameAddressExist(AddressDto addressDto, User user) {
       var address = user.getAddress();
        return addressDto.getAddressLine1().equalsIgnoreCase(address.getAddressLine1())
                && addressDto.isAddressLine2Equal(address.getAddressLine2())
                && addressDto.getPincode().equalsIgnoreCase(address.getPinCode())
                && addressDto.getCity().equalsIgnoreCase(address.getCity())
                && addressDto.getState().equalsIgnoreCase(address.getState());
    }

    public AddressDto update(AddressDto addressDto) throws InvalidInputException, ServerException {
        var address = fetchById(addressDto.getId());
        ObjectMapperUtils.map(addressDto, address);
        address = addressRepository.save(address);
        addressDto = ObjectMapperUtils.map(address, AddressDto.class);
        return addressDto;
    }

    public Address fetchById(String addressId) throws InvalidInputException {
        return addressRepository.findById(addressId).
                orElseThrow(()->new InvalidInputException("Address not found with id "+addressId));
    }
}
