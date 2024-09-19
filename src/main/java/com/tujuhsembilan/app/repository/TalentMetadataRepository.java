package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentMetadata;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentMetadataRepository extends JpaRepository<TalentMetadata, UUID > {
    TalentMetadata findByTalent(Talent talent);
}
