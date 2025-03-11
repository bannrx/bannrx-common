package com.bannrx.common.utilities;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {

    /**
     * Method to fetch logged in user
     *
     * @return if security context set then authUser Object else string anonymousUser
     */
    public static Object getLoggedInUser(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
