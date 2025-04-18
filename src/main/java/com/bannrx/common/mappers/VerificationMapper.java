package com.bannrx.common.mappers;

import com.bannrx.common.dtos.verification.VerificationDto;
import com.bannrx.common.persistence.entities.VerificationAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import rklab.utility.utilities.JsonUtils;

@Mapper(imports = JsonUtils.class)
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
    @Mapping(target = "data", expression = "java(JsonUtils.jsonOf(verificationDto.getVerificationData()))")
    @Mapping(target = "process", source = "verificationDto.verificationData.process")
    VerificationAudit toEntity(
            final VerificationDto verificationDto,
            final String verifiedBy
            );

}
