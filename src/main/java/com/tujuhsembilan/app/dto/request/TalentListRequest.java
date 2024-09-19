package com.tujuhsembilan.app.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TalentListRequest {

    private UUID talentId;
    private String talentName;
    private Integer sortBy;
    private Integer filter;
}
