package com.bannrx.common.service;

import com.bannrx.common.dtos.BusinessDto;
import com.bannrx.common.enums.BusinessType;
import com.bannrx.common.enums.Status;
import com.bannrx.common.persistence.entities.Business;
import com.bannrx.common.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ObjectMapperUtils;

import java.util.Objects;


@Service
@Loggable
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;;

    public Business save(BusinessDto businessDto) throws ServerException {
        var business = ObjectMapperUtils.map(businessDto, Business.class);
        business.setActive(true);
        business.setType(BusinessType.FOOD_AND_BEVERAGE);
        return businessRepository.save(business);
    }

    public Business toEntity(BusinessDto businessDto) throws ServerException {
        return ObjectMapperUtils.map(businessDto, Business.class);
    }

    public BusinessDto toDto(Business business) throws ServerException {
        if (Objects.nonNull(business)){
            return ObjectMapperUtils.map(business, BusinessDto.class);
        }
        return null;
    }
}
