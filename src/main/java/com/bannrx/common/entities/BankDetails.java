package com.bannrx.common.entities;

import com.bannrx.common.dtos.Persist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import rklab.utility.utilities.JsonUtils;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetails extends Persist {

    @Column(name = "account_no")
    private Long accountNo;

    @Column(name = "IFSC_code")
    private String IFSCCode;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "verification_process_id")
    private String verificationProcessId;

    
    public BankDetails(String createdBy,String modifiedBy){
        super(createdBy,modifiedBy);
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }

}
