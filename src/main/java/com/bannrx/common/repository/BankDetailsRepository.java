package com.bannrx.common.repository;

import com.bannrx.common.persistence.entities.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, String> {

    boolean existsByAccountNoAndIfscCode(final String accountNo, final String ifscCode);

}
