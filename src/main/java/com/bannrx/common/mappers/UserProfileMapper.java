package com.bannrx.common.mappers;

import com.bannrx.common.dtos.user.BDAUserExcelDto;
import com.bannrx.common.enums.UserRole;
import com.bannrx.common.persistence.entities.UserProfile;
import org.mapstruct.Mapper;


@Mapper
public interface UserProfileMapper {

    UserProfile toEntity(
            final BDAUserExcelDto bdaUserExcelDto,
            final String password,
            final UserRole role
    );
}