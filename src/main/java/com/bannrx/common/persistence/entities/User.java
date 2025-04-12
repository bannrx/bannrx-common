package com.bannrx.common.persistence.entities;

import com.bannrx.common.enums.UserRole;
import com.bannrx.common.persistence.Persist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.JsonUtils;
import java.util.*;
import java.util.List;



@EqualsAndHashCode(callSuper = true, exclude = {"addresses", "bankDetails", "business", "authToken"})
@Data
@Entity
@Table (
        name = "user",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email"),
                @Index(name = "idx_user_phone", columnList = "phone_no")
        }
)
public class User extends Persist {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Address> addresses = new HashSet<>();

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BankDetails> bankDetails = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    @JsonIgnore
    private Business business;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_token_id", referencedColumnName = "id")
    @JsonIgnore
    private AuthToken authToken;

    @Override
    protected String setDefaultModifiedBy(){
        var retVal = super.setDefaultModifiedBy();
        if (StringUtils.isBlank(retVal)){
            retVal = this.email;
        }
        return retVal;
    }

    @JsonIgnore
    public void appendAddress(Address address) {
        if (Objects.nonNull(address)){
            var existing = Optional.ofNullable(this.getAddresses())
                    .orElse(new LinkedHashSet<>());
            address.setUser(this);
            existing.add(address);
            this.setAddresses(existing);
        }
    }

    @JsonIgnore
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUser(null);
    }

    @JsonIgnore
    public void appendBankDetail(BankDetails bankDetails) {
        if (Objects.nonNull(bankDetails)){
            var existing = Optional.ofNullable(this.getBankDetails())
                    .orElse(new LinkedHashSet<>());
            bankDetails.setUser(this);
            existing.add(bankDetails);
            this.setBankDetails(existing);
        }
    }

    @JsonIgnore
    public void removeBankDetail(BankDetails bankDetail) {
        bankDetails.remove(bankDetail);
        bankDetail.setUser(null);
    }

    @Override
    @JsonIgnore
    public String getPrefix() {
        return "UR";
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }
}
