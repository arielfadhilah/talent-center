package com.tujuhsembilan.app.dto.request;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestApproval {
    private String sortBy;
    private Integer pageSize, pageNumber;

    public Pageable getPage() {
        int pageNumberValue = (pageNumber != null) ? pageNumber < 1 ? 1 : pageNumber : 1;
        int pageSizeValue = (pageSize != null) ? pageSize < 1 ? 1 : pageSize : 10;

        Map<String, Sort> sortOptions = new HashMap<>();
        sortOptions.put("requestDate,asc", Sort.by(Sort.Order.asc("requestDate")));
        sortOptions.put("requestDate,desc", Sort.by(Sort.Order.desc("requestDate")));
        sortOptions.put("agencyName,asc", Sort.by(Sort.Order.asc("talentWishlist.client.agencyName")));
        sortOptions.put("agencyName,desc", Sort.by(Sort.Order.desc("talentWishlist.client.agencyName")));
        sortOptions.put("talentName,asc", Sort.by(Sort.Order.asc("talentWishlist.talent.talentName")));
        sortOptions.put("talentName,desc", Sort.by(Sort.Order.desc("talentWishlist.talent.talentName")));
        sortOptions.put("approvalStatus,asc", Sort.by(Sort.Order.asc("talentRequestStatus.talentRequestStatusName")));
        sortOptions.put("approvalStatus,desc", Sort.by(Sort.Order.desc("talentRequestStatus.talentRequestStatusName")));

        Sort sort = sortOptions.get(sortBy);

        if (sort == null) {
            sort = Sort.by(Sort.Order.desc("requestDate"), Sort.Order.desc("talentWishlist.talent.talentName"));
        }

        return PageRequest.of(pageNumberValue - 1, pageSizeValue, sort);
    }

}
