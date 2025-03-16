package com.bannrx.common.persistence.entities;

import com.bannrx.common.persistence.Persist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rklab.utility.utilities.JsonUtils;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class BankDetails extends Persist {

    @Column(name = "account_no")
    private Long accountNo;

    @Column(name = "IFSC_code")
    private String IFSCCode;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "verification_process_id")
    private String verificationProcessId;

    @Override
    @JsonIgnore
    public String getPrefix() {
        return "BD";
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }

}
