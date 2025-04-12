package com.bannrx.common.specifications;

import com.bannrx.common.persistence.entities.User;
import com.bannrx.common.searchCriteria.UserSearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import static rklab.utility.constants.GlobalConstants.Symbols.PERCENTAGE;

public class UserSpecification {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PHONE = "phoneNo";

    public static Specification<User> buildSearchCriteria(final UserSearchCriteria searchCriteria){
        var retVal = Specification.<User>where(null);
        if (StringUtils.isNotBlank(searchCriteria.getName())){
            retVal = retVal.and(filterByNameLike(searchCriteria.getName()));
        }
        if (StringUtils.isNotBlank(searchCriteria.getEmail())){
            retVal = retVal.and(filterByEmail(searchCriteria.getEmail()));
        }
        if (StringUtils.isNotBlank(searchCriteria.getPhoneNo())){
            retVal = retVal.and(filterByPhoneLike(searchCriteria.getPhoneNo()));
        }
        if (StringUtils.isNotBlank(searchCriteria.getLoggedInUserId())){
            retVal = retVal.and(filterOutLoggedInUser(searchCriteria.getLoggedInUserId()));
        }
        return retVal;
    }

    private static Specification<User> filterByNameLike(final String name){
        return (((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(NAME),withLikePattern(name)))
        );
    }

    private static Specification<User> filterByEmail(final String email){
        return (((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(EMAIL),email))
        );
    }

    private static Specification<User> filterByPhoneLike(final String phone){
        return (((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(PHONE),phone+PERCENTAGE))
        );
    }

    private static Specification<User> filterOutLoggedInUser(final String userId){
        return (((root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get(ID),userId))
        );
    }

    private static String withLikePattern(String str){
        return PERCENTAGE + str + PERCENTAGE;
    }

}
