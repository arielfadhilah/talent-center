package com.tujuhsembilan.app.dto.response;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalResponse {

    private UUID talentRequestId;
    private String agencyName;
    private Date requestDate;
    private String talentName;
    private String approvalStatus;
}
