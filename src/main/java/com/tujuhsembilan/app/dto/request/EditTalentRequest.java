package com.tujuhsembilan.app.dto.request;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.tujuhsembilan.app.dto.response.PositionResponse;
import com.tujuhsembilan.app.dto.response.TalentSkillsetResponse;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditTalentRequest {

    private UUID talentId;
    private String talentPhotoFilename;
    @Size(min = 1, max = 255, message = "Panjang kolom tidak boleh melebihi 255 karakter")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Kolom tidak boleh berisi special character/angka")
    private String talentName;
    private UUID talentStatus;
    private String nip;
    private String sex;
    private Date dob;
    private String talentDescription;
    private String cv;
    private Integer talentExperience;
    private UUID talentLevelId;
    private Integer projectCompleted;
    private List<PositionResponse> position; 
    private List<TalentSkillsetResponse> skillSet;
    private String email;
    private String cellphone;
    private UUID employeeStatusId; 
    private Boolean talentAvailability; 
    private String videoUrl; 
     
}
