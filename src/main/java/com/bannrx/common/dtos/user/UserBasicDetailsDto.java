package com.bannrx.common.dtos.user;

import com.bannrx.common.validationGroups.AvailableValidationGroup;
import com.bannrx.common.validationGroups.UpdateValidationGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static rklab.utility.constants.GlobalConstants.RegexPattern.PHONE_NO_REGEX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBasicDetailsDto {

    @NotEmpty(message = "User Id is mandatory.", groups = UpdateValidationGroup.class)
    private String id;

    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
    private String name;

    @NotBlank(message = "Phone no cannot be blank")
    @Pattern(regexp = PHONE_NO_REGEX, message = "Phone no must be 10 digits.", groups = AvailableValidationGroup.class)
    private String phoneNo;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email must be a valid email address.", groups = AvailableValidationGroup.class)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters.", groups = AvailableValidationGroup.class)
    private String password;

}
