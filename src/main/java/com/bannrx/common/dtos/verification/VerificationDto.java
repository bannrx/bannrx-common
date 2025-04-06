package com.bannrx.common.dtos.verification;

import com.bannrx.common.enums.VerificationPurpose;
import com.bannrx.common.validationGroups.UpdateValidationGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationDto implements Serializable {

    @NotEmpty(message = "Verification id is mandatory.", groups = UpdateValidationGroup.class)
    private String id;
    @NotNull(message = "Please provide data to be verified.")
    private VerificationData verificationData;
    @NotNull(message = "Verification Purpose is mandatory.")
    private VerificationPurpose purpose;
    private boolean verified;

}
