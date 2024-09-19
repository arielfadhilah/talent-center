package com.tujuhsembilan.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "talent_metadata", schema = "public")
public class TalentMetadata {

    @Id
    private UUID talentId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @Column(name = "cv_counter")
    private Integer cvCounter;

    @Column(name = "profile_counter")
    private Integer profileCounter;

    @Column(name = "total_project_completed")
    private Integer totalProjectCompleted;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedTime;
}

