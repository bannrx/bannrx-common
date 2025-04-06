package com.bannrx.common.repository;

import com.bannrx.common.persistence.entities.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
}
