package com.bannrx.common.persistence.entities;

import com.bannrx.common.enums.UserRole;
import com.bannrx.common.persistence.Persist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import rklab.utility.utilities.JsonUtils;


@EqualsAndHashCode(callSuper = true, exclude = {"authToken"})
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_token_id", referencedColumnName = "id")
    @JsonIgnore
    private AuthToken authToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    private UserProfile userProfile;

    public void createProfile() {
        if (this.userProfile == null) {
            this.userProfile = new UserProfile();
            this.userProfile.setUser(this);
        }
    }

    @Override
    protected String setDefaultModifiedBy(){
        var retVal = super.setDefaultModifiedBy();
        if (StringUtils.isBlank(retVal)){
            retVal = this.email;
        }
        return retVal;
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
