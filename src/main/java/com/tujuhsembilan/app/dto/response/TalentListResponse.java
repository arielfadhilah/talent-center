package com.tujuhsembilan.app.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentListResponse {
    
    private long total;
    private List<TalentResponse> data;
    private String message;
    private int statusCode;
    private String status;

}
