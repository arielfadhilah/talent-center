package com.tujuhsembilan.app.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "talent_level", schema = "public")
public class TalentLevel {

    @Id
    @Column(name = "talent_level_id", columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID talentLevelId;

    @Column(name = "talent_level_name")
    private String talentLevelName;

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

    @OneToMany(mappedBy = "talentLevel")
    private List<Talent> talents;
}



