package com.bannrx.common.persistence.entities;

import com.bannrx.common.enums.Unit;
import com.bannrx.common.persistence.Persist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rklab.utility.utilities.JsonUtils;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Dimension extends Persist {

    @Column(name = "length", precision = 10, scale = 2)
    private BigDecimal length;

    @Column(name = "breadth", precision = 10 , scale = 2)
    private BigDecimal breadth;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private Unit unit;

    @Override
    public String getPrefix() {
        return "DI";
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }
}
