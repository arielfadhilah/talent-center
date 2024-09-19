package com.tujuhsembilan.app.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employee_status", schema = "public")
public class EmployeeStatus {

    @Id
    @Column(name = "employee_status_id", columnDefinition = "CHAR(36)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID employeeStatusId;

    @Column(name = "employee_status_name")
    private String employeeStatusName;

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

    @OneToMany(mappedBy = "employeeStatus")
    private List<Talent> talents;
}


