package com.bannrx.common.persistence.entities;

import com.bannrx.common.enums.TokenStatus;
import com.bannrx.common.persistence.Persist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rklab.utility.utilities.JsonUtils;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true, exclude = {"user"})
@Data
@Entity
@Table(
        name = "auth_token",
        indexes = {
                @Index(name = "idx_token", columnList = "token")
        }
)
public class AuthToken extends Persist {

    @Column(name = "token", nullable = false)
    @JsonIgnore
    private String token;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    @OneToOne(mappedBy = "authToken")
    @JsonIgnore
    private User user;


    @Override
    public String getPrefix() {
        return "AT";
    }

    @Override
    public String toString(){
        return JsonUtils.jsonOf(this);
    }

    @JsonIgnore
    public void map(User user){
        if (Objects.nonNull(user)){
            user.setAuthToken(this);
            this.setUser(user);
        }
    }

}
