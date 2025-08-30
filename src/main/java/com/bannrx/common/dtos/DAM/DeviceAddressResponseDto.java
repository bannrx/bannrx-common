package com.bannrx.common.dtos.DAM;

import com.bannrx.common.dtos.AddressDto;
import com.bannrx.common.dtos.device.DeviceDto;
import com.bannrx.common.enums.DeviceStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DAMResponseDto {
    private String DAMId;
    private DeviceDto deviceDto;
    private AddressDto addressDto;
    private DeviceStatus status;
}
