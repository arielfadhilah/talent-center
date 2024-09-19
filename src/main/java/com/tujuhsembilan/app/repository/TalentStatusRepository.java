package com.tujuhsembilan.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.model.TalentStatus;

public interface TalentStatusRepository extends JpaRepository<TalentStatus, UUID> {
    Optional<TalentStatus> findByTalentStatusName(String name);
}
