package com.tujuhsembilan.app.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApprovalRequest {

    private UUID talentRequestId;
    private UUID action;
    private String rejectReason;
}
