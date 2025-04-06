package com.bannrx.common.mappers;

import com.bannrx.common.dtos.verification.VerificationDto;
import com.bannrx.common.persistence.entities.VerificationAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VerificationMapper {

    /**
     * The constant INSTANCE.
     */
    VerificationMapper INSTANCE = Mappers.getMapper(VerificationMapper.class);

    /**
     * To entity verification audit.
     *
     * @param verificationDto the verification dto
     * @param verifiedBy      the verified by
     * @return the verification audit
     */
    @Mapping(target = "data", source = "verificationDto.verificationData")
    @Mapping(target = "process", source = "verificationDto.verificationData.process")
    VerificationAudit toEntity(
            final VerificationDto verificationDto,
            final String verifiedBy
            );

    @Mapping(target = "verificationData", source = "verificationAudit.data")
    VerificationDto toDto(
            final VerificationAudit verificationAudit
    );

}
