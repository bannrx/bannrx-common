package com.bannrx.common.repository;

import com.bannrx.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankDetailsRepository extends JpaRepository<User, String> {
}
