package com.bannrx.common.service;

import com.bannrx.common.persistence.entities.SystemConfig;
import com.bannrx.common.repository.SystemConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rklab.utility.expectations.InvalidInputException;

@Service
@RequiredArgsConstructor
public class SystemConfigService {

    private final SystemConfigRepository systemConfigRepository;


    /**
     * Fetches the configuration value for the give configuration key
     *
     * @param configKey configuration key
     * @return System Config entity
     * @throws InvalidInputException in case no entity found with the defined key
     */
    public SystemConfig getSystemConfig(String configKey) throws InvalidInputException {
        var systemConfig = systemConfigRepository.findByKey(configKey);
        if (systemConfig.isEmpty()) {
            throw new InvalidInputException("No Configuration defined for key " + configKey);
        }
        return systemConfig.get();
    }

}
