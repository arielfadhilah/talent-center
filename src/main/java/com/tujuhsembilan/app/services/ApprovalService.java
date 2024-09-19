package com.tujuhsembilan.app.services;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dto.request.ApprovalRequest;
import com.tujuhsembilan.app.dto.request.PageRequestApproval;
import com.tujuhsembilan.app.dto.response.ApprovalListResponse;
import com.tujuhsembilan.app.dto.response.ApprovalResponse;
import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.model.TalentRequest;
import com.tujuhsembilan.app.model.TalentRequestStatus;
import com.tujuhsembilan.app.repository.ApprovalRepository;
import com.tujuhsembilan.app.repository.ApprovalRequestRepo;
import com.tujuhsembilan.app.repository.TalentRequestStatusRepository;

@Service
public class ApprovalService {

    @Autowired
    private TalentRequestStatusRepository talentRequestStatusRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ApprovalRequestRepo approvalRequestRepo;

    @Autowired
    private MessageSource messageSource;

    private static final UUID APPROVE_UUID = UUID.fromString("23ab9ae7-f0cb-45f4-b7ab-ef6f10adbdd8");

    public ResponseEntity<ApprovalListResponse> getApprovals(PageRequestApproval pageRequest) {
        try {
            Pageable pageable = pageRequest.getPage();
            Specification<TalentRequest> spec = (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // Dummy specification, adjust as needed

            Page<TalentRequest> pagedApprovals = approvalRepository.findAll(spec, pageable);

            List<ApprovalResponse> approvalList = pagedApprovals.stream().map(approval -> ApprovalResponse.builder()
                    .talentRequestId(approval.getTalentRequestId())  // Corrected method name
                    .talentName(approval.getTalentWishlist().getTalent().getTalentName())
                    .agencyName(approval.getTalentWishlist().getClient().getAgencyName())
                    .approvalStatus(mapStatus(approval.getTalentRequestStatus().getTalentRequestStatusName()))
                    .requestDate(approval.getRequestDate())
                    .build()).collect(Collectors.toList());  // Using collect to convert to list

            long totalData = pagedApprovals.getTotalElements();
            String message = messageSource.getMessage("approval.get.list.success", null, Locale.getDefault());
            return ResponseEntity
                    .ok(new ApprovalListResponse(totalData, approvalList, message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
            String message = messageSource.getMessage("approval.get.list.error", null, Locale.getDefault());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApprovalListResponse(0, null, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<MessageResponse> approveOrRejectApproval(ApprovalRequest approvalRequest) {
        try {
            TalentRequest talentRequest = approvalRequestRepo.findById(approvalRequest.getTalentRequestId())
                    .orElseThrow(() -> new RuntimeException("TalentRequest not found"));

            TalentRequestStatus status = talentRequestStatusRepository.findById(approvalRequest.getAction())
                    .orElseThrow(() -> new RuntimeException("TalentRequestStatus not found"));

            talentRequest.setTalentRequestStatus(status);
            if ("Rejected".equalsIgnoreCase(status.getTalentRequestStatusName())) {
                talentRequest.setRequestRejectReason(approvalRequest.getRejectReason());
            } else {
                talentRequest.setRequestRejectReason(null);
            }

            approvalRequestRepo.save(talentRequest);

            String message = messageSource.getMessage(
                    APPROVE_UUID.equals(approvalRequest.getAction()) ? "approval.request.approve.success" : "approval.request.reject.success",
                    null,
                    Locale.getDefault()
            );

            return ResponseEntity.ok(MessageResponse.builder()
                    .message(message)
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK.getReasonPhrase())
                    .build());

        } catch (Exception e) {
            String message = messageSource.getMessage("approval.request.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageResponse.builder()
                    .message(message)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .build());
        }
    }

    private String mapStatus(String status) {
        switch (status.toLowerCase()) {
            case "approve":
                return "Approved";
            case "reject":
                return "Rejected";
            default:
                return status;
        }
    }

}
