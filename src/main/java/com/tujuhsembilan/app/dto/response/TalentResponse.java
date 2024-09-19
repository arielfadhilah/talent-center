package com.tujuhsembilan.app.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TalentResponse {
    private UUID talentId;
    private String talentPhotoFilename; 
    private String talentName;
    private String talentStatus; 
    private String employeeStatus; 
    private Boolean talentAvailability; 
    private Integer talentExperience; 
    private String talentLevel; 
    private List<PositionResponse> position; // Updated to match "position"
    private List<TalentSkillsetResponse> skillSet; // Updated to match "skillSet"
}
