package com.bannrx.common.repository;

import com.bannrx.common.persistence.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends
        JpaRepository<Document, String>,
        JpaSpecificationExecutor<Document>
{}
