package com.tujuhsembilan.app.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@Table(name = "talent_skillset", schema = "public")
public class TalentSkillset {

    @OneToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "skillset_id")
    private Skillset skillset;

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

