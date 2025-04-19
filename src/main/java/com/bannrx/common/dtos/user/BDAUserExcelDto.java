package com.bannrx.common.dtos.user;

import lombok.Data;
import rklab.utility.annotations.ColumnNotEmpty;



@Data
public class BDAUserExcelDto {
    @ColumnNotEmpty(message = "name should not be empty", column = 0)
    private String name;

    @ColumnNotEmpty(message = "phone no should not be empty", column = 1)
    private String phoneNo;

    @ColumnNotEmpty(message = "Email should not be empty", column = 2)
    private String email;

    @ColumnNotEmpty(message = "AddressLine1 should not be empty", column = 3)
    private String addressLine1;

    @ColumnNotEmpty(message = "city should not be empty", column = 4)
    private String city;

    @ColumnNotEmpty(message = "state should not be empty", column = 5)
    private String state;

    @ColumnNotEmpty(message = "pincode should not be empty", column = 6)
    private String pinCode;

    @ColumnNotEmpty(message = "account no should not be empty", column = 7)
    private String accountNo;

    @ColumnNotEmpty(message = "ifsc code should not be empty", column = 8)
    private String ifscCode;
}
