package com.bannrx.common.dtos;

import com.bannrx.common.dtos.verification.VerificationData;
import com.bannrx.common.validationGroups.AddValidationGroup;
import com.bannrx.common.validationGroups.UpdateValidationGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.bannrx.common.enums.VerificationProcess.MANUAL;
import static rklab.utility.constants.GlobalConstants.RegexPattern.IFSC_REGEX;



@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankDetailsDto extends VerificationData {

    @NotEmpty(groups = UpdateValidationGroup.class, message = "Bank Details Id is mandatory.")
    private String id;

    @NotNull(message = "Account number cannot be null")
    private String accountNo;

    @NotEmpty(message = "IFSC code cannot be blank")
    @Size(min = 11, max = 11, message = "IFSC code must be 11 characters long")
    @Pattern(regexp = IFSC_REGEX, message = "IFSC code must be in the format AAAA0XXXXXX (4 letters, 0, 6 alphanumeric characters)")
    private String ifscCode;

    @NotNull(message = "Verified status cannot be null")
    private Boolean verified;

    @NotEmpty(message = "Verification process ID cannot be blank", groups = {AddValidationGroup.class, UpdateValidationGroup.class})
    private String verificationProcessId;

    @Override
    public void setDefaultVerificationProcess() {
        this.setProcess(MANUAL);
    }
}
