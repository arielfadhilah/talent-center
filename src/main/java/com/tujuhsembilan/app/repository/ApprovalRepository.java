package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.model.TalentRequest;

public interface ApprovalRepository extends JpaRepository<TalentRequest, UUID> {
    Page<TalentRequest> findAll(Specification<TalentRequest> spec, Pageable pageable);
}
