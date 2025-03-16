package com.bannrx.common.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;

/**
 * The responsibility of this service is to only fetch the system defined configuration properties in application.yml
 */

@Service
@Loggable
public class SystemVariableService {

    @Value("${spring.profiles.active:local}")
    @Getter
    private String activeProfile;

}
