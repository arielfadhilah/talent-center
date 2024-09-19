package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name = "talent_wishlist", schema = "public")
public class TalentWishlist {

    @Id
    @Column(name = "talent_wishlist_id", columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID talentWishlistId;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "wishlist_date")
    private Date wishlistDate;

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

    @OneToOne(mappedBy = "talentWishlist", cascade = CascadeType.ALL)
    @JsonIgnore
    private TalentRequest talentrequest;
}


