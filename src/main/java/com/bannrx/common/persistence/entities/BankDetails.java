package com.bannrx.common.persistence.entities;

import com.bannrx.common.persistence.Persist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import rklab.utility.utilities.JsonUtils;


@EqualsAndHashCode(callSuper = true, exclude = "userProfile")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "bank_details",
        indexes = {
                @Index(name = "idx_ac_ifsc", columnList = "account_no, ifscCode")
        }
)
public class BankDetails extends Persist {

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "verification_process_id")
    private String verificationProcessId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @Override
    @JsonIgnore
    public String getPrefix() {
        return "BD";
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }

}
