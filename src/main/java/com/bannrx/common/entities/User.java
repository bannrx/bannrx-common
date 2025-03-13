package com.bannrx.common.entities;

import com.bannrx.common.dtos.Persist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import rklab.utility.utilities.JsonUtils;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class User extends Persist {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    //generic
    public User(String createdBy,String modifiedBy){
        super(createdBy,modifiedBy);
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }

}
