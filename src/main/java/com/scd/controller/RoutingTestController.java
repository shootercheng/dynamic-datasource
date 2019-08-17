package com.scd.controller;

import com.scd.mapper.TestMapper;
import com.scd.mapper.UserMapper;
import com.scd.model.po.Test;
import com.scd.model.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chengdu
 * @date 2019/8/15.
 */
@RestController
public class RoutingTestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TestMapper testMapper;

    @RequestMapping(value = "/test/type", method = RequestMethod.POST)
    public List<User> getUsers(){
        return userMapper.getAll();
    }

    @RequestMapping(value = "/test/method", method = RequestMethod.POST)
    public User findOneUser(Long id){
        return userMapper.getOne(id);
    }

    @RequestMapping(value = "/test/code", method = RequestMethod.POST)
    public int countNum(){
        return userMapper.countNum();
    }

    @RequestMapping(value = "/test/default", method = RequestMethod.POST)
    public Test testDefault(Long id){
        return testMapper.selectByPrimaryKey(id);
    }

    @RequestMapping(value = "/test/methodonly", method = RequestMethod.POST)
    public int testMethodOnly(){
        Test test = new Test();
        test.setName("cd" + System.currentTimeMillis());
        int j = testMapper.insert(test);
        int i = testMapper.insertSelective(test);
        return i + j;
    }
}
