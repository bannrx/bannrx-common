package com.bannrx.common.mappers;

import com.bannrx.common.dtos.device.DeviceDto;
import com.bannrx.common.persistence.entities.Device;
import com.bannrx.common.persistence.entities.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface DeviceMapper {
    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    Device toEntity(DeviceDto dto);
}
