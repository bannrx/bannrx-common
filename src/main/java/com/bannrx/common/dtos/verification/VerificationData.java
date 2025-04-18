package com.bannrx.common.dtos.verification;

import com.bannrx.common.enums.VerificationProcess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * All the verification concrete data that concrete service provider will parse needs to extend this.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class VerificationData{

    @NotNull(message = "process is mandatory.")
    private VerificationProcess process;

    @JsonIgnore
    public abstract void setDefaultVerificationProcess();

}
