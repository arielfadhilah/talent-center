package com.tujuhsembilan.app.dto.request;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddTalentRequest {

    private String talentPhoto;
    private String talentName;
    private UUID talentStatusId;
    private String nip;
    private String sex;
    private Date dob;
    private String talentDescription;
    private String cv;
    private Integer talentExperience;
    private UUID talentLevelId;
    private List<PositionRequest> position;
    private List<SkillsetRequest> skillSet;
    private String email;
    private String cellphone;
    private UUID employeeStatusId;
    private Boolean talentAvailability;
    private String videoUrl;
    private Integer projectCompleted;
}
