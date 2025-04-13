package com.bannrx.common.mappers;

import com.bannrx.common.dtos.BDAUserExcelDto;
import com.bannrx.common.enums.UserRole;
import com.bannrx.common.persistence.entities.Address;
import com.bannrx.common.persistence.entities.BankDetails;
import com.bannrx.common.persistence.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDetailsMapper {

    UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);

    User toEntity(
            final BDAUserExcelDto bdaUserExcelDto,
            final String password,
            final UserRole role
            );

    @Mapping(target = "IFSCCode", source = "bdaUserExcelDto.ifscCode")
    BankDetails toEntity(
            final BDAUserExcelDto bdaUserExcelDto,
            final boolean verified
    );

    Address toEntity(
            final BDAUserExcelDto bdaUserExcelDto
    );

}
