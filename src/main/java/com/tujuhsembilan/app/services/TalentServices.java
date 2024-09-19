package com.tujuhsembilan.app.services;

import com.tujuhsembilan.app.exception.ResourceNotFoundException;
import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Position;
import com.tujuhsembilan.app.model.Skillset;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.model.TalentMetadata;
import com.tujuhsembilan.app.model.TalentStatus;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentMetadataRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;
import com.tujuhsembilan.app.services.Spesifications.TalentSpesification;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;

import com.tujuhsembilan.app.dto.request.AddTalentRequest;
import com.tujuhsembilan.app.dto.request.EditTalentRequest;
import com.tujuhsembilan.app.dto.request.PageRequest;
import com.tujuhsembilan.app.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TalentServices {

        @Autowired
        private MinioClient minioClient;

        @Value("${application.minio.bucketName}")
        private String bucketName;

        @Autowired
        private TalentRepository talentRepository;

        @Autowired
        private TalentRequestRepository talentRequestRepository;

        @Autowired
        private TalentMetadataRepository talentMetadataRepository;

        @Autowired
        private TalentStatusRepository talentStatusRepository;

        @Autowired
        private EmployeeStatusRepository employeeStatusRepository;

        @Autowired
        private TalentLevelRepository talentLevelRepository;

        @Autowired
        private PositionRepository positionRepository;

        @Autowired
        private SkillsetRepository skillsetRepository;

        @Autowired
        private MessageSource messageSource;

        public ResponseEntity<TalentListResponse> getTalents(List<UUID> tags, List<UUID> filterPosition,
                                                     String filterLevel, String filterExperience, String filterStatus, String filterEmployee,
                                                     String sortBy, String nameSearch, int page, int row) {
        try {
                Specification<Talent> spec = TalentSpesification.talentFilter(tags, filterPosition, filterLevel,
                        filterExperience, filterStatus, filterEmployee, sortBy, nameSearch);

                PageRequest pageRequest = PageRequest.builder()
                        .sortBy(sortBy)
                        .pageSize(row)
                        .pageNumber(page)
                        .search(nameSearch)
                        .level(filterLevel)
                        .experience(filterExperience)
                        .status(filterStatus)
                        .employeeStatus(filterEmployee)
                        .build();

                Pageable pageable = pageRequest.getPage();

                Page<Talent> pagedTalents = talentRepository.findAll(spec, pageable);

                List<TalentResponse> talentList = pagedTalents.map(talent -> TalentResponse.builder()
                        .talentId(talent.getTalentId())
                        .talentPhotoFilename(talent.getTalentPhotoFileName())
                        .talentName(talent.getTalentName())
                        .talentStatus(talent.getTalentStatus() != null ? talent.getTalentStatus().getTalentStatusName() : "N/A")
                        .employeeStatus(talent.getEmployeeStatus() != null ? talent.getEmployeeStatus().getEmployeeStatusName() : "N/A")
                        .talentLevel(talent.getTalentLevel() != null ? talent.getTalentLevel().getTalentLevelName() : "N/A")
                        .talentAvailability(talent.getTalentAvailability())
                        .talentExperience(talent.getExperience())
                        .position(talent.getPositions().stream()
                                .map(position -> PositionResponse.builder()
                                        .positionId(position.getPositionId())
                                        .positionName(position.getPositionName())
                                        .build())
                                .collect(Collectors.toList()))
                        .skillSet(talent.getSkillset().stream()
                                .map(skillSet -> TalentSkillsetResponse.builder()
                                        .skillId(skillSet.getSkillsetId())
                                        .skillName(skillSet.getSkillsetName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build()).toList();

                long totalData = pagedTalents.getTotalElements();
                String message = messageSource.getMessage("talent.get.list.success", null, Locale.getDefault());
                return ResponseEntity
                        .ok(new TalentListResponse(totalData, talentList, message,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
                log.error(e.getMessage(), e);
                String message = messageSource.getMessage("talent.get.list.error", null, Locale.getDefault());
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new TalentListResponse(0, null,
                                message, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
                }
        }


        public TalentDetailResponse getTalentDetail(UUID talentId) {
            Talent talent = talentRepository.findById(talentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Talent not found"));
    
            TalentMetadata talentMetadata = talentMetadataRepository.findByTalent(talent);
            int totalRequested = talentRequestRepository.countByTalentWishlist_Talent(talent);
    
            return TalentDetailResponse.builder()
                    .talentId(talent.getTalentId())
                    .talentPhoto(talent.getTalentPhotoFileName())
                    .talentName(talent.getTalentName())
                    .talentStatus(talent.getTalentStatus() != null ? talent.getTalentStatus().getTalentStatusName() : "N/A")
                    .nip(talent.getEmployeeNumber())
                    .sex(talent.getGender())
                    .dob(talent.getBirthDate())
                    .talentDescription(talent.getTalentDescription())
                    .cv(talent.getTalentCvFileName())
                    .talentExperience(talent.getExperience())
                    .talentLevel(talent.getTalentLevel() != null ? talent.getTalentLevel().getTalentLevelName() : "N/A")
                    .projectCompleted(talentMetadata != null ? talentMetadata.getTotalProjectCompleted() : 0)
                    .totalRequested(totalRequested)
                    .position(talent.getPositions().stream()
                                .map(position -> PositionResponse.builder()
                                        .positionId(position.getPositionId())
                                        .positionName(position.getPositionName())
                                        .build())
                                .collect(Collectors.toList()))
                    .skillSet(talent.getSkillset().stream()
                                .map(skillSet -> TalentSkillsetResponse.builder()
                                        .skillId(skillSet.getSkillsetId())
                                        .skillName(skillSet.getSkillsetName())
                                        .build())
                                .collect(Collectors.toList()))
                    .email(talent.getEmail())
                    .cellphone(talent.getCellphone())
                    .employeeStatus(talent.getEmployeeStatus() != null ? talent.getEmployeeStatus().getEmployeeStatusName() : "N/A")
                    .talentAvailability(talent.getTalentAvailability())
                    .videoUrl(talent.getBiographyVideoUrl())
                    .build();
        }

        @Transactional
        public ResponseEntity<MessageResponse> editTalentById(EditTalentRequest editTalentRequest, MultipartFile photo, MultipartFile cv) {
        try {
            log.info("Editing talent: {}", editTalentRequest);

            Talent existingTalent = talentRepository.findById(editTalentRequest.getTalentId())
                    .orElseThrow(() -> new RuntimeException("Talent not found: " + editTalentRequest.getTalentId()));

            log.info("Finding TalentStatus with ID: {}", editTalentRequest.getTalentStatus());
            TalentStatus talentStatus = talentStatusRepository.findById(editTalentRequest.getTalentStatus())
                    .orElseThrow(() -> new RuntimeException("TalentStatus not found: " + editTalentRequest.getTalentStatus()));

            log.info("Finding TalentLevel with ID: {}", editTalentRequest.getTalentLevelId());
            TalentLevel talentLevel = talentLevelRepository.findById(editTalentRequest.getTalentLevelId())
                    .orElseThrow(() -> new RuntimeException("TalentLevel not found: " + editTalentRequest.getTalentLevelId()));

            log.info("Finding EmployeeStatus with ID: {}", editTalentRequest.getEmployeeStatusId());
            EmployeeStatus employeeStatus = employeeStatusRepository.findById(editTalentRequest.getEmployeeStatusId())
                    .orElseThrow(() -> new RuntimeException("EmployeeStatus not found: " + editTalentRequest.getEmployeeStatusId()));

            List<Position> positions = editTalentRequest.getPosition().stream()
                    .map(p -> {
                        log.info("Finding Position with ID: {}", p.getPositionId());
                        return positionRepository.findById(p.getPositionId())
                                .orElseThrow(() -> new RuntimeException("Position not found: " + p.getPositionId()));
                    })
                    .collect(Collectors.toList());

            List<Skillset> skillSets = editTalentRequest.getSkillSet().stream()
                    .map(s -> {
                        log.info("Finding Skillset with ID: {}", s.getSkillId());
                        return skillsetRepository.findById(s.getSkillId())
                                .orElseThrow(() -> new RuntimeException("Skillset not found: " + s.getSkillId()));
                    })
                    .collect(Collectors.toList());

            String photoFileName = existingTalent.getTalentPhotoFileName();
            String cvFileName = existingTalent.getTalentCvFileName();

            if (photo != null && !photo.isEmpty()) {
                photoFileName = uploadFileToMinio(photo, "");
            }
            if (cv != null && !cv.isEmpty()) {
                cvFileName = uploadFileToMinio(cv, "");
            }

            existingTalent.setTalentPhotoFileName(photoFileName);
            existingTalent.setTalentName(editTalentRequest.getTalentName());
            existingTalent.setTalentStatus(talentStatus);
            existingTalent.setEmployeeNumber(editTalentRequest.getNip());
            existingTalent.setGender(editTalentRequest.getSex());
            existingTalent.setBirthDate(editTalentRequest.getDob());
            existingTalent.setTalentDescription(editTalentRequest.getTalentDescription());
            existingTalent.setTalentCvFileName(cvFileName);
            existingTalent.setExperience(editTalentRequest.getTalentExperience());
            existingTalent.setTalentLevel(talentLevel);
            existingTalent.setPositions(positions);
            existingTalent.setSkillset(skillSets);
            existingTalent.setEmail(editTalentRequest.getEmail());
            existingTalent.setCellphone(editTalentRequest.getCellphone());
            existingTalent.setEmployeeStatus(employeeStatus);
            existingTalent.setIsActive(true);
            existingTalent.setTalentAvailability(editTalentRequest.getTalentAvailability());
            existingTalent.setBiographyVideoUrl(editTalentRequest.getVideoUrl());

            TalentMetadata talentMetadata = existingTalent.getTalentMetadata();
            if (talentMetadata == null) {
                talentMetadata = new TalentMetadata();
                talentMetadata.setTalent(existingTalent);
            }
            talentMetadata.setTotalProjectCompleted(editTalentRequest.getProjectCompleted());
            existingTalent.setTalentMetadata(talentMetadata);

            talentRepository.save(existingTalent);

            String message = messageSource.getMessage("talent.put.edit.talentdata.success", null, Locale.getDefault());
            String formattedMessage = MessageFormat.format(message, existingTalent.getTalentName(), existingTalent.getTalentId().toString());
            MessageResponse response = MessageResponse.builder()
                    .message(formattedMessage)
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK.getReasonPhrase())
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error editing talent: ", e);
            String message = messageSource.getMessage("talent.put.edit.talentdata.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageResponse.builder()
                    .message(message)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .build());
        }
    }

        public ResponseEntity<MessageResponse> addNewTalent(AddTalentRequest addTalentRequest, MultipartFile photo, MultipartFile cv) {
                try {
                        log.info("Saving new talent: {}", addTalentRequest);

                        log.info("Finding TalentStatus with ID: {}", addTalentRequest.getTalentStatusId());
                        TalentStatus talentStatus = talentStatusRepository.findById(addTalentRequest.getTalentStatusId()).orElseThrow(() -> new RuntimeException("TalentStatus not found: " + addTalentRequest.getTalentStatusId()));

                        log.info("Finding TalentLevel with ID: {}", addTalentRequest.getTalentLevelId());
                        TalentLevel talentLevel = talentLevelRepository.findById(addTalentRequest.getTalentLevelId()).orElseThrow(() -> new RuntimeException("TalentLevel not found: " + addTalentRequest.getTalentLevelId()));

                        log.info("Finding EmployeeStatus with ID: {}", addTalentRequest.getEmployeeStatusId());
                        EmployeeStatus employeeStatus = employeeStatusRepository.findById(addTalentRequest.getEmployeeStatusId()).orElseThrow(() -> new RuntimeException("EmployeeStatus not found: " + addTalentRequest.getEmployeeStatusId()));

                        List<Position> positions = addTalentRequest.getPosition().stream()
                                .map(p -> {
                                        log.info("Finding Position with ID: {}", p.getPositionId());
                                        return positionRepository.findById(p.getPositionId()).orElseThrow(() -> new RuntimeException("Position not found: " + p.getPositionId()));
                                })
                                .collect(Collectors.toList());

                        List<Skillset> skillSets = addTalentRequest.getSkillSet().stream()
                                .map(s -> {
                                        log.info("Finding Skillset with ID: {}", s.getSkillId());
                                        return skillsetRepository.findById(s.getSkillId()).orElseThrow(() -> new RuntimeException("Skillset not found: " + s.getSkillId()));
                                })
                                .collect(Collectors.toList());

                        String photoFileName = null;
                        String cvFileName = null;

                        if (photo != null && !photo.isEmpty()) {
                                photoFileName = uploadFileToMinio(photo, "");
                        }
                        if (cv != null && !cv.isEmpty()) {
                                cvFileName = uploadFileToMinio(cv, "");
                        }

                        Talent newTalent = Talent.builder()
                                .talentPhotoFileName(photoFileName)
                                .talentName(addTalentRequest.getTalentName())
                                .talentStatus(talentStatus)
                                .employeeNumber(addTalentRequest.getNip())
                                .gender(addTalentRequest.getSex())
                                .birthDate(addTalentRequest.getDob())
                                .talentDescription(addTalentRequest.getTalentDescription())
                                .talentCvFileName(cvFileName)
                                .experience(addTalentRequest.getTalentExperience())
                                .talentLevel(talentLevel)
                                .positions(positions)
                                .skillset(skillSets)
                                .email(addTalentRequest.getEmail())
                                .cellphone(addTalentRequest.getCellphone())
                                .employeeStatus(employeeStatus)
                                .isActive(true)
                                .talentAvailability(addTalentRequest.getTalentAvailability())
                                .biographyVideoUrl(addTalentRequest.getVideoUrl())
                                .build();

                        TalentMetadata talentMetadata = TalentMetadata.builder()
                                .talent(newTalent)
                                .cvCounter(0)
                                .profileCounter(0)
                                .totalProjectCompleted(addTalentRequest.getProjectCompleted())
                                .build();

                        newTalent.setTalentMetadata(talentMetadata);

                        talentRepository.save(newTalent);

                        String message = messageSource.getMessage("talent.post.new.talentdata.success", null, Locale.getDefault());
                        String formattedMessage = MessageFormat.format(message, newTalent.getTalentName(), newTalent.getTalentId().toString());
                        MessageResponse response = MessageResponse.builder()
                                .message(formattedMessage)
                                .statusCode(HttpStatus.OK.value())
                                .status(HttpStatus.OK.getReasonPhrase())
                                .build();

                        return ResponseEntity.ok(response);

                } catch (Exception e) {
                        log.error("Error saving talent: ", e);
                        String message = messageSource.getMessage("talent.post.new.talentdata.error", null, Locale.getDefault());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageResponse.builder()
                                .message(message)
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                .build());
                }
    }

       private String uploadFileToMinio(MultipartFile file, String folder) {
        try {
            String objectName = folder + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }
            return objectName;
        } catch (Exception e) {
            log.error("Failed to upload file to MinIO: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Failed to upload file to MinIO", e);
        }
    }

}


//         @Transactional
//         public MessageResponse addTalent(AddTalentRequest request) {
//                 // Validate file extensions
//                 validateFileExtensions(request.getTalentPhoto(), request.getCv());

//                 // Generate file names
//                 String photoFilename = generateFilename(request.getTalentName(), request.getTalentExperience(), request.getTalentLevelId(), request.getTalentPhoto());
//                 String cvFilename = generateFilename(request.getTalentName(), request.getTalentExperience(), request.getTalentLevelId(), request.getCv());

//                 // Create new Talent
//                 Talent newTalent = new Talent();
//                 newTalent.setTalentPhotoFilename(photoFilename);
//                 newTalent.setTalentName(request.getTalentName());
//                 newTalent.setTalentStatus(talentStatusRepository.findById(request.getTalentStatusId()).orElseThrow(() -> new ResourceNotFoundException("Talent Status not found")));
//                 newTalent.setEmployeeNumber(request.getNip());
//                 newTalent.setGender(request.getSex());
                
//                 // Convert dob to LocalDateTime
//                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//                 LocalDateTime birthDate = LocalDateTime.parse(request.getDob(), formatter);
//                 newTalent.setBirthDate(birthDate);
                
//                 newTalent.setTalentDescription(request.getTalentDescription());
//                 newTalent.setTalentCvFilename(cvFilename);
//                 newTalent.setExperience(request.getTalentExperience());
//                 newTalent.setTalentLevel(talentLevelRepository.findById(request.getTalentLevelId()).orElseThrow(() -> new ResourceNotFoundException("Talent Level not found")));
//                 newTalent.setEmail(request.getEmail());
//                 newTalent.setCellphone(request.getCellphone());
//                 newTalent.setEmployeeStatus(employeeStatusRepository.findById(request.getEmployeeStatusId()).orElseThrow(() -> new ResourceNotFoundException("Employee Status not found")));
//                 newTalent.setTalentAvailability(request.getTalentAvailability());
//                 newTalent.setBiographyVideoUrl(request.getVideoUrl());
//                 newTalent.setIsActive(true);

//         // Set positions
//         List<TalentPosition> positions = request.getPosition().stream()
//                 .map(positionResponse -> {
//                         Position position = positionRepository.findById(positionResponse.getPositionId())
//                                 .orElseThrow(() -> new ResourceNotFoundException("Position not found"));
//                         return new TalentPosition(newTalent, position);
//                 })
//                 .collect(Collectors.toList());
//         newTalent.setTalentPositions(positions);

//         // Set skill sets
//         List<TalentSkillset> skillSets = request.getSkillSet().stream()
//                 .map(skillsetResponse -> {
//                         Skillset skillset = skillsetRepository.findById(skillsetResponse.getSkillId())
//                                 .orElseThrow(() -> new ResourceNotFoundException("Skillset not found"));
//                         return new TalentSkillset(newTalent, skillset);
//                 })
//                 .collect(Collectors.toList());
//         newTalent.setTalentSkillsets(skillSets);

//         // Save talent
//         talentRepository.save(newTalent);

//         // Insert into talent_metadata
//         TalentMetadata talentMetadata = TalentMetadata.builder()
//                 .id(newTalent.getTalentId())
//                 .talent(newTalent)
//                 .cvCounter(0)
//                 .profileCounter(0)
//                 .totalProjectCompleted(request.getProjectCompleted())
//                 .createdTime(LocalDateTime.now())
//                 .build();
//         talentMetadataRepository.save(talentMetadata);

//         return new MessageResponse("Talent added successfully", HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());

//         }

//         private void validateFileExtensions(String photoFilename, String cvFilename) {
//                 if (!photoFilename.matches(".*\\.(jpg|jpeg|png)$")) {
//                         throw new InvalidFileExtensionException("Photo file extension must be .jpg, .jpeg, or .png");
//                 }
//                 if (!cvFilename.matches(".*\\.(pdf|docx)$")) {
//                         throw new InvalidFileExtensionException("CV file extension must be .pdf or .docx");
//                 }
//         }

//         private String generateFilename(String talentName, int talentExperience, UUID talentLevelId, String originalFilename) {
//                 String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                 return talentName.replaceAll(" ", "_") + "_" + talentExperience + "_" + talentLevelId + "_" + System.currentTimeMillis() + extension;
//         }

//         public ApprovalListResponse getApprovals(String filter, String sortBy, Pageable pageable) {
//                 Page<TalentRequest> talentRequests;
        
//                 if (filter == null || filter.isEmpty()) {
//                     talentRequests = talentRequestRepository.findAll(pageable);
//                 } else {
//                     talentRequests = talentRequestRepository.findFilteredAndSorted(filter, sortBy, pageable);
//                 }
        
//                 List<ApprovalResponse> approvalResponses = talentRequests.getContent().stream()
//                         .map(this::convertToApprovalResponse)
//                         .collect(Collectors.toList());
        
//                 return ApprovalListResponse.builder()
//                         .total(talentRequests.getTotalElements())
//                         .data(approvalResponses)
//                         .build();
//             }

//     private ApprovalResponse convertToApprovalResponse(TalentRequest talentRequest) {
//         return ApprovalResponse.builder()
//                 .talentRequestId(talentRequest.getTalentRequestId())
//                 .agencyName(talentRequest.getTalentWishlist().getClient().getAgencyName()) 
//                 .requestDate(talentRequest.getRequestDate().toLocalDate())
//                 .talentName(talentRequest.getTalentWishlist().getTalent().getTalentName()) // Assuming TalentStatus has getTalentStatusName method
//                 .approvalStatus(talentRequest.getTalentRequestStatus().getTalentRequestStatusName()) // Assuming EmployeeStatus has getEmployeeStatusName method
//                 .build();
//     }
