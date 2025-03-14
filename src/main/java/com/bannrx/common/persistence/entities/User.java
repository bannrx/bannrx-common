package com.bannrx.common.persistence.entities;

import com.bannrx.common.persistence.Persist;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import rklab.utility.utilities.JsonUtils;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class User extends Persist {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Override
    public String setDefaultModifiedBy(){
        var retVal = super.setDefaultModifiedBy();
        if (StringUtils.isBlank(retVal)){
            retVal = this.phoneNo;
        }
        return retVal;
    }

    @Override
    public String getPrefix() {
        return "UR";
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }

}
