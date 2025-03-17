package com.bannrx.common.dtos;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    @NotEmpty(message = "Name is mandatory")
    private String name;
    @NotEmpty(message = "Phone no is mandatory.")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone no.")
    private String phoneNo;
    @NotEmpty(message = "Email is mandatory.")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Invalid email"
    )
    private String email;
    @NotEmpty(message = "Password is mandatory.")
    private String password;

}
