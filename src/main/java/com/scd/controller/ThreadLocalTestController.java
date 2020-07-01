package com.scd.controller;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.scd.service.ThreadLocalService;
import com.scd.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author James
 */
@RestController
public class ThreadLocalTestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLocalTestController.class);

    @Autowired
    private ThreadLocalService threadLocalService;

    public static final ThreadLocal<String> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static final ThreadLocal<String> TRANAMITT_THREAD_LOCAL = new TransmittableThreadLocal<>();

    @RequestMapping(value = "/async/value/same", method = RequestMethod.GET)
    public String testThreadLocal() {
        HttpServletRequest request = RequestUtil.getHttpRequest();
        String dbCode = request.getHeader("db-code");
        LOGGER.info("parent thread db code is {}", dbCode);
        THREAD_LOCAL.set(dbCode);
        threadLocalService.asyncTest();
        return "success";
    }

    @RequestMapping(value = "/async/value/diff", method = RequestMethod.GET)
    public String testTransmittThreadLocal() {
        HttpServletRequest request = RequestUtil.getHttpRequest();
        String dbCode = request.getHeader("db-code");
        LOGGER.info("parent thread db code is {}", dbCode);
        TRANAMITT_THREAD_LOCAL.set(dbCode);
        threadLocalService.transmittTest();
        return "success";
    }
}
