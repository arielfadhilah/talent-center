package com.tujuhsembilan.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nimbusds.jose.shaded.gson.Gson;
import com.tujuhsembilan.app.dto.request.AddTalentRequest;
import com.tujuhsembilan.app.dto.request.ApprovalRequest;
import com.tujuhsembilan.app.dto.request.EditTalentRequest;
import com.tujuhsembilan.app.dto.request.PageRequestApproval;
import com.tujuhsembilan.app.dto.response.ApprovalListResponse;
import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.dto.response.TalentDetailResponse;
import com.tujuhsembilan.app.dto.response.TalentListResponse;
import com.tujuhsembilan.app.services.ApprovalService;
import com.tujuhsembilan.app.services.TalentServices;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/talent-management")
public class TalentController {

    @Autowired
    private TalentServices talentServices;

    @Autowired
    private ApprovalService approvalService;

    @GetMapping("/talents")
    public ResponseEntity<TalentListResponse> getTalents(
            @RequestParam(required = false) List<UUID> tags,
            @RequestParam(required = false) List<UUID> filterPosition,
            @RequestParam(required = false) String filterLevel,
            @RequestParam(required = false) String filterExperience,
            @RequestParam(required = false) String filterStatus,
            @RequestParam(required = false) String filterEmployee,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String talentName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int row) {

        return talentServices.getTalents(tags, filterPosition, filterLevel, filterExperience, filterStatus,
                filterEmployee, sortBy, talentName, page, row);
    }


    @GetMapping("/talents/{talentId}")
    public ResponseEntity<TalentDetailResponse> getTalentDetail(@PathVariable UUID talentId) {
        TalentDetailResponse talentDetailResponse = talentServices.getTalentDetail(talentId);
        return new ResponseEntity<>(talentDetailResponse, HttpStatus.OK);
    }

    @PutMapping(path = "/talents", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<MessageResponse> editTalentData(
            @RequestPart("replacement") String data,
            @RequestPart(name = "photo", required = false) MultipartFile photoFile,
            @RequestPart(name = "cv", required = false) MultipartFile pdfFile) {

        EditTalentRequest editTalentRequest = new Gson().fromJson(data, EditTalentRequest.class);

        return talentServices.editTalentById(editTalentRequest, photoFile, pdfFile);
    }

    @PostMapping(path = "/talents", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<MessageResponse> addNewTalent(
            @RequestPart("newData") String data,
            @RequestPart(name = "photo", required = false) MultipartFile photoFile,
            @RequestPart(name = "pdf", required = false) MultipartFile pdfFile) {

        AddTalentRequest addTalentRequest = new Gson().fromJson(data, AddTalentRequest.class);

        return talentServices.addNewTalent(addTalentRequest, photoFile, pdfFile);
    }

    @GetMapping("/approvals")
    public ResponseEntity<ApprovalListResponse> getApprovals(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int row) {

        PageRequestApproval pageRequest = PageRequestApproval.builder()
                .sortBy(sortBy)
                .pageNumber(page)
                .pageSize(row)
                .build();

        return approvalService.getApprovals(pageRequest);
    }

    @PutMapping("/talent-approvals")
    public ResponseEntity<MessageResponse> approveOrRejectApproval(@RequestBody ApprovalRequest approvalRequest) {
        return approvalService.approveOrRejectApproval(approvalRequest);
    }

}
