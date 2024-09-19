package com.tujuhsembilan.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse {

    private int currentPage;
    private int totalPages;
    private int pageSize;
    private int startIndex;
    private int endIndex;
}
