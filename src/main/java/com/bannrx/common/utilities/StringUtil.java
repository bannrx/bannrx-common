package com.bannrx.common.utilities;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@UtilityClass
public class StringUtil {

    public static boolean isNullOrEmpty(String str){
        return (Objects.isNull(str) || StringUtils.isEmpty(str));
    }

    public static boolean isNotBlank(String str){
        return StringUtils.isNotBlank(str);
    }


}
