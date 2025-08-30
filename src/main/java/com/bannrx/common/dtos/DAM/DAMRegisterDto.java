package com.bannrx.common.dtos.DAM;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RegisterDAMDto {
    
    private String deviceId;
    private String addressId;
}
