package com.scd.util;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chengdu
 * @date 2019/8/15.
 */
public class RequestUtil {

    /**
     * 返回值可能为 NULL
     * @return
     */
    public static HttpServletRequest getHttpRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest httpServletRequest = null;
        if (requestAttributes instanceof ServletRequestAttributes){
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            httpServletRequest = servletRequestAttributes.getRequest();
        }
        return httpServletRequest;
    }
}
