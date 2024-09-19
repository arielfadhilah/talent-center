package com.tujuhsembilan.app.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentLevelResponse {

    private UUID talentLevelId;
    private String talentLevelName;
}
