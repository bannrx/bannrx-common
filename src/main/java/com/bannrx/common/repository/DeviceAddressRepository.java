package com.bannrx.common.repository;

import com.bannrx.common.persistence.entities.DeviceAddressMapper;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DAMRepository extends JpaRepository<DeviceAddressMapper, String> {
}
