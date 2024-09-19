package com.tujuhsembilan.app.dto.response;

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
public class TalentDetailResponse {
    private UUID talentId;
    private String talentPhoto;
    private String talentName;
    private String talentStatus;
    private String nip; 
    private String sex; 
    private Date dob; 
    private String talentDescription;
    private String cv;
    private int talentExperience;
    private String talentLevel;
    private int projectCompleted;
    private int totalRequested;
    private List<PositionResponse> position; 
    private List<TalentSkillsetResponse> skillSet; 
    private String email;
    private String cellphone;
    private String employeeStatus;
    private Boolean talentAvailability;
    private String videoUrl; 
}
