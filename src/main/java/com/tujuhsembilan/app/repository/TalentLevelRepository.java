package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tujuhsembilan.app.model.TalentLevel;

import org.springframework.stereotype.Repository;

@Repository
public interface TalentLevelRepository extends JpaRepository<TalentLevel, UUID> {
}
