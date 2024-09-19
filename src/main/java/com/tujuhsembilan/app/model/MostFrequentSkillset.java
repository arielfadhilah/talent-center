package com.tujuhsembilan.app.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "most_frequent_skillset", schema = "public")
public class MostFrequentSkillset {

    @Id
    @Column(name = "most_frequent_skillset_id", columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID mostFrequentSkillsetId;

    @ManyToOne
    @JoinColumn(name = "skillset_id")
    private Skillset skillset;

    @Column(name = "counter")
    private Integer counter;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdTime;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedTime;
}



