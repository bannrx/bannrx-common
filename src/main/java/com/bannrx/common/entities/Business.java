package com.bannrx.common.entities;

import com.bannrx.common.dtos.Persist;
import com.bannrx.common.enums.BusinessType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import rklab.utility.utilities.JsonUtils;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Business extends Persist {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private BusinessType type;


    public Business(String createdBy,String modifiedBy){
        super(createdBy,modifiedBy);
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }

}
