package com.bannrx.common.persistence.entities;

import com.bannrx.common.dtos.verification.VerificationData;
import com.bannrx.common.enums.VerificationProcess;
import com.bannrx.common.enums.VerificationPurpose;
import com.bannrx.common.persistence.Persist;
import com.bannrx.common.persistence.converters.VerificationDataConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "verification_audit")
public class VerificationAudit extends Persist {

    @Column(name = "purpose", nullable = false)
    @Enumerated(EnumType.STRING)
    private VerificationPurpose purpose;

    @Column(name = "process", nullable = false)
    @Enumerated(EnumType.STRING)
    private VerificationProcess process;

    @Column(name = "verification_data", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = VerificationDataConverter.class)
    private VerificationData data;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column(name = "verified_by", nullable = false)
    private String verifiedBy;

    @Override
    public String getPrefix() {
        return "VA";
    }
}


