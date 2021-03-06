package com.scd.aop;

import com.scd.config.ParamConfig;
import com.scd.util.InheritableHeaderValueUtil;
import com.scd.util.RequestUtil;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengdu
 * @date 2019/11/24
 */
@Component
@Aspect
@Order(3)
public class RequestAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAop.class);

    @Autowired
    private ParamConfig paramConfig;

    // @within TYPE
    // @annotation METHOD
    @Pointcut(value = "@within(com.scd.annotation.InheritableRequest)||@annotation(com.scd.annotation.InheritableRequest)")
    public void requestPointcut(){
    }

    @Before(value = "requestPointcut()")
    public void doBefore() {
        HttpServletRequest httpServletRequest = RequestUtil.getHttpRequest();
        Map<String, String> map = new HashMap<>(16);
        String dbHeaderKey = paramConfig.getDsRouterHederKey();
        map.put(dbHeaderKey, httpServletRequest.getHeader(dbHeaderKey));
        LOGGER.info("transform request header to headervalue {}", map);
        InheritableHeaderValueUtil.setHeaderMap(map);
    }

    @AfterReturning(value = "requestPointcut()", returning = "object")
    public void doAfterReturn(Object object) {
        LOGGER.info("request after return {}", object);
    }

}
