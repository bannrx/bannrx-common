package com.bannrx.common.repository;

import com.bannrx.common.persistence.entities.VerificationAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationAuditRepository extends JpaRepository<VerificationAudit, String> {
}
