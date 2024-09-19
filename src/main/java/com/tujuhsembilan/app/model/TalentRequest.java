package com.tujuhsembilan.app.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "talent_request", schema = "public")
public class TalentRequest {

    @Id
    @Column(name = "talent_request_id", columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID talentRequestId;

    @ManyToOne
    @JoinColumn(name = "talent_request_status_id")
    private TalentRequestStatus talentRequestStatus;

    @OneToOne
    @JoinColumn(name = "talent_wishlist_id")
    private TalentWishlist talentWishlist;

    @Column(name = "request_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @Column(name = "request_reject_reason")
    private String requestRejectReason;

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

