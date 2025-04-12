package com.bannrx.common.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import rklab.utility.models.searchCriteria.PageableSearchCriteria;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableResponse<T> {
    private int perPage;
    private int pageNo;
    private String sortBy;
    private String sortDirection;
    private List<T> content;
    private Integer count;

    public PageableResponse(
            final List<T> resultList,
            final PageableSearchCriteria searchCriteria
    ){
        this.perPage = searchCriteria.getPerPage();
        this.pageNo = searchCriteria.getPageNo();
        this.sortBy = searchCriteria.getSortBy();
        this.sortDirection = searchCriteria.getSortDirection().name();
        this.content = resultList;
        this.count = resultList.size();
    }
}
