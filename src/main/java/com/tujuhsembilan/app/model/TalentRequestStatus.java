package com.tujuhsembilan.app.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Data
@Entity
@Table(name = "talent_request_status", schema = "public")
public class TalentRequestStatus {

    @Id
    @Column(name = "talent_request_status_id", columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID talentRequestStatusId;

    @Column(name = "talent_request_status_name")
    private String talentRequestStatusName;

    @Column(name = "is_active")
    private boolean isActive;

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

    @OneToMany(mappedBy = "talentRequestStatus")
    private List<TalentRequest> talentRequests;
}


