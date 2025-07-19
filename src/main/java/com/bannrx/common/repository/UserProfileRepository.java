package com.bannrx.common.repository;

import com.bannrx.common.persistence.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
}
