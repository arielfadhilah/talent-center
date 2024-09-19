package com.tujuhsembilan.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "client_position", schema = "public")
public class ClientPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_position_id", columnDefinition = "CHAR(36)")
    private UUID clientPositionId;

    @Column(name = "client_position_name")
    private String clientPositionName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    private Timestamp lastModifiedTime;

    @OneToMany(mappedBy = "clientPosition")
    private List<Client> clients;
}


