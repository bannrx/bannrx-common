package com.bannrx.common.repository;

import com.bannrx.common.persistence.entities.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface DimensionRepository extends JpaRepository<Dimension, String>,
        JpaSpecificationExecutor<Dimension> {
}
