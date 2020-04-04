package com.scd.controller;

import com.scd.mapper.TestUserMapper;
import com.scd.model.po.Edge;
import com.scd.model.po.TestUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author James
 */
// ServletInvocableHandlerMethod
// org.springframework.http.converter.StringHttpMessageConverter
@RestController
public class ParamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamController.class);

    @Autowired
    private TestUserMapper testUserMapper;

    @RequestMapping(value = "/param/edge", method = RequestMethod.POST)
    public void testParam(Edge edge) {
        LOGGER.info("param edge {}", edge);
    }

    @RequestMapping(value = "/param/body/edge", method = RequestMethod.POST)
    public void testParamReqBody(@RequestBody Edge edge) {
        LOGGER.info("param edge {}", edge);
    }

    @RequestMapping(value = "/param/user", method = RequestMethod.POST)
    public void testUser(@RequestBody TestUser testUser) {
        testUserMapper.insertSelective(testUser);
    }
}
