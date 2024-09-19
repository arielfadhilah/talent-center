package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentRequest;

@Repository
public interface TalentRequestRepository extends JpaRepository<TalentRequest, UUID> {
    Page<TalentRequest> findByRequestDateContainingIgnoreCase(String talentName, Pageable pageable);
    
    @Query("SELECT tr FROM TalentRequest tr WHERE " +
           "tr.talentWishlist.client.agencyName LIKE %:filter% OR " +
           "tr.talentWishlist.talent.talentName LIKE %:filter% OR " +
           "tr.talentRequestStatus.talentRequestStatusName LIKE %:filter% " +
           "ORDER BY " +
           "CASE WHEN :sortBy = 'date' THEN tr.requestDate END DESC, " +
           "CASE WHEN :sortBy = 'agency' THEN tr.talentWishlist.client.agencyName END ASC")
    Page<TalentRequest> findFilteredAndSorted(@Param("filter") String filter, @Param("sortBy") String sortBy, Pageable pageable);
    int countByTalentWishlist_Talent(Talent talent);

}
