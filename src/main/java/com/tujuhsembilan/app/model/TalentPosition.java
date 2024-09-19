package com.tujuhsembilan.app.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.*;
import lombok.*;

@Data
@Table(name = "talent_position", schema = "public")
public class TalentPosition {

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

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


