package com.bannrx.common.dtos.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rklab.utility.services.JwtConfiguration;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTConfigProperties implements JwtConfiguration {

    private String secret;
    private Date expiry;


    @Override
    public String getSecretKey() {
        return secret;
    }

    @Override
    public Date getExpiration() {
        return expiry;
    }
}
