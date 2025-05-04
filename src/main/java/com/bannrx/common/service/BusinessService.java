package com.bannrx.common.service;

import com.bannrx.common.dtos.BusinessDto;
import com.bannrx.common.persistence.entities.Business;
import com.bannrx.common.persistence.entities.User;
import com.bannrx.common.repository.BusinessRepository;
import com.bannrx.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ObjectMapperUtils;

import java.util.Objects;


@Service
@Loggable
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    public Business toEntity(BusinessDto businessDto) throws ServerException {
        return ObjectMapperUtils.map(businessDto, Business.class);
    }

    /**
     * To dto business dto.
     *
     * @param business the business
     * @return the business dto
     * @throws ServerException the server exception
     */
    public BusinessDto toDto(Business business) throws ServerException {
        if (Objects.nonNull(business)){
            return ObjectMapperUtils.map(business, BusinessDto.class);
        }
        return null;
    }

    public BusinessDto update(BusinessDto businessDto) throws ServerException, InvalidInputException {
        var business = fetchById(businessDto.getId());
        ObjectMapperUtils.map(businessDto, business);
        business = businessRepository.save(business);
        return toDto(business);
    }

    private Business fetchById(String id) throws InvalidInputException {
        return businessRepository.findById(id)
                .orElseThrow(()-> new InvalidInputException("Business Details is not found with this id "+ id));
    }

    public void validate(BusinessDto businessDto, String loggedInUserId) throws InvalidInputException {
        var user = fetchUserById(loggedInUserId);
        var businessId = user.getBusiness().getId();
        if (!StringUtils.equals(businessId, businessDto.getId())){
            throw new UnsupportedOperationException("Business Details are associated to other user.");
        }
    }

    private User fetchUserById(String id) throws InvalidInputException {
        return userRepository.findById(id)
                .orElseThrow(()-> new InvalidInputException("User is not found with this id "+ id));
    }
}
