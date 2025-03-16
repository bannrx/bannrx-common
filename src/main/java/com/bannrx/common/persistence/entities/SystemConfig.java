package com.bannrx.common.persistence.entities;

import com.bannrx.common.persistence.Persist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "system_config", indexes = {
        @Index(name = "idx_config_key", columnList = "config_key")
})
public class SystemConfig extends Persist {

    @Column(name = "config_key")
    private String key;

    @Column(name = "config_value")
    private String value;

    @Override
    public String getPrefix() {
        return "SC";
    }
}

