package com.bannrx.common.dtos.verification;

import com.bannrx.common.enums.VerificationProcess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * All the verification concrete data that concrete service provider will parse needs to extend this.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "process",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PasswordVerificationData.class, name = "PASSWORD")
})
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class VerificationData{
    @JsonIgnore
    @NotNull(message = "process is mandatory.")
    private VerificationProcess process;

    @JsonIgnore
    public abstract void setDefaultVerificationProcess();

}
