package com.tujuhsembilan.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.model.TalentRequestStatus;

@Repository
public interface TalentRequestStatusRepository extends JpaRepository<TalentRequestStatus, UUID>{
    @Query("SELECT t FROM TalentRequestStatus t WHERE LOWER(t.talentRequestStatusName) = LOWER(:talentRequestStatusName)")
    Optional<TalentRequestStatus> findByTalentRequestStatusName(String talentRequestStatusName);
}
