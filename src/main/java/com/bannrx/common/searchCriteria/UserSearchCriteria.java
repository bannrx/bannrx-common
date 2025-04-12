package com.bannrx.common.searchCriteria;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import rklab.utility.models.searchCriteria.PageableSearchCriteria;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserSearchCriteria extends PageableSearchCriteria {

    private String name;
    private String phoneNo;
    private String email;
    private String loggedInUserId;

    @Builder(builderMethodName = "userSearchCriteriaBuilder")
    public UserSearchCriteria(
            final String name,
            final String phoneNo,
            final String email,
            final int perPage,
            final int pageNo,
            final String sortBy,
            final Sort.Direction sortDirection
    ){
        super(perPage, pageNo, sortBy, sortDirection);
        this.name= name;
        this.phoneNo= phoneNo;
        this.email= email;
    }

}
