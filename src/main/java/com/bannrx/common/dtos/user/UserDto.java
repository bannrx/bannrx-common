package com.bannrx.common.dtos.user;

import com.bannrx.common.dtos.AddressDto;
import com.bannrx.common.dtos.BankDetailsDto;
import com.bannrx.common.dtos.BusinessDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserDto extends UserBasicDetailsDto{

    @NotNull(message = "Address list cannot be null.")
    @Size(min = 1, message = "At least one address is required.")
    @Valid
    private Set<AddressDto> addressDtoSet;

    @NotNull(message = "Bank details list cannot be null.")
    @Size(min = 1, message = "At least one bank detail is required.")
    @Valid
    private Set<BankDetailsDto> bankDetailsDtoSet;

    @NotNull(message = "Business details cannot be null.")
    @Valid
    private BusinessDto businessDto;

}
