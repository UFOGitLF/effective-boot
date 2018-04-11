package com.fly.common.validator;

import com.fly.common.exception.RrException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 * @author liufei
 */
public abstract class AbstractAssert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RrException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RrException(message);
        }
    }
}
