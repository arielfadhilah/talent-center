package com.tujuhsembilan.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequest {
    private String sortBy;
    private Integer pageSize, pageNumber;
    private String search; // Untuk pencarian berdasarkan nama
    private String level; // Untuk filter level (Senior, Junior)
    private String experience; // Untuk filter pengalaman (0-1 Tahun, 2-4 Tahun, 5+ Tahun)
    private String status; // Untuk filter status (On Site, Non Onsite)
    private String employeeStatus; // Untuk filter kepegawaian (Active, Non Active)

    public Pageable getPage() {
        int pageNumberValue = (pageNumber != null) ? (pageNumber < 1 ? 1 : pageNumber) : 1;
        int pageSizeValue = (pageSize != null) ? (pageSize < 1 ? 1 : pageSize) : 10;

        Map<String, Sort> sortOptions = new HashMap<>();
        sortOptions.put("talent,asc", Sort.by(Sort.Order.asc("talentName")));
        sortOptions.put("talent,desc", Sort.by(Sort.Order.desc("talentName")));
        sortOptions.put("level,asc", Sort.by(Sort.Order.asc("talentLevel.talentLevelName")));
        sortOptions.put("level,desc", Sort.by(Sort.Order.desc("talentLevel.talentLevelName")));
        sortOptions.put("experience,asc", Sort.by(Sort.Order.asc("experience")));
        sortOptions.put("experience,desc", Sort.by(Sort.Order.desc("experience")));
        sortOptions.put("status,asc", Sort.by(Sort.Order.asc("talentStatus.talentStatusName")));
        sortOptions.put("status,desc", Sort.by(Sort.Order.desc("talentStatus.talentStatusName")));
        sortOptions.put("employeeStatus,asc", Sort.by(Sort.Order.asc("employeeStatus.employeeStatusName")));
        sortOptions.put("employeeStatus,desc", Sort.by(Sort.Order.desc("employeeStatus.employeeStatusName")));

        Sort sort = sortOptions.get(sortBy);

        if (sort == null) {
            sort = Sort.by(Sort.Order.desc("experience"), Sort.Order.desc("talentLevel.talentLevelName"));
        }

        return org.springframework.data.domain.PageRequest.of(pageNumberValue - 1, pageSizeValue, sort);
    }
}
