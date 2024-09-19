package com.tujuhsembilan.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "talent", schema = "public")
public class Talent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "talent_id", columnDefinition = "CHAR(36)")
    private UUID talentId;

    @ManyToOne
    @JoinColumn(name = "talent_level_id")
    private TalentLevel talentLevel;

    @ManyToOne
    @JoinColumn(name = "talent_status_id")
    private TalentStatus talentStatus;

    @ManyToOne
    @JoinColumn(name = "employee_status_id")
    private EmployeeStatus employeeStatus;

    @Column(name = "talent_name")
    private String talentName;

    @Column(name = "talent_photo_filename")
    private String talentPhotoFileName;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "gender", columnDefinition = "bpchar")
    private String gender;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "talent_description", columnDefinition = "TEXT")
    private String talentDescription;

    @Column(name = "talent_cv_filename")
    private String talentCvFileName;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "email")
    private String email;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "biography_video_url")
    private String biographyVideoUrl;

    @Column(name = "is_add_to_list_enable")
    private Boolean isAddToListEnable;

    @Column(name = "talent_availability")
    private Boolean talentAvailability;

    @Column(name = "is_active")
    private Boolean isActive;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "talent_position", joinColumns = @JoinColumn(name = "talent_id"), inverseJoinColumns = @JoinColumn(name = "position_id"))
    private List<Position> positions;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "talent_skillset", joinColumns = @JoinColumn(name = "talent_id"), inverseJoinColumns = @JoinColumn(name = "skillset_id"))
    private List<Skillset> skillset;

    @OneToOne(mappedBy = "talent", cascade = CascadeType.ALL)
    @JsonIgnore
    private TalentMetadata talentMetadata;

    @OneToMany(mappedBy = "talent")
    private List<TalentWishlist> talentWishlists;
}

