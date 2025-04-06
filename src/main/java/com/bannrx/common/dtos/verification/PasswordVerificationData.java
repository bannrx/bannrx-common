package com.bannrx.common.dtos.verification;

import com.bannrx.common.dtos.requests.PasswordLoginRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import static com.bannrx.common.enums.VerificationProcess.PASSWORD;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class PasswordVerificationData extends VerificationData {

    @NotEmpty(message = "Username is mandatory.")
    private String username;
    @NotEmpty(message = "Password is mandatory.")
    private String password;

    public PasswordVerificationData(PasswordLoginRequest request){
        this.password = request.getPassword();
        this.username = request.getUsername();
        this.setDefaultVerificationProcess();
    }

    @JsonIgnore
    @Override
    public void setDefaultVerificationProcess() {
        this.setProcess(PASSWORD);
    }
}

