package com.scd.controller;

import com.scd.annotation.InheritableRequest;
import com.scd.mapper.TestMapper;
import com.scd.mapper.UserMapper;
import com.scd.model.po.Article;
import com.scd.model.po.Test;
import com.scd.model.po.User;
import com.scd.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @Autowired
    private ArticleService articleService;

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

    @RequestMapping(value = "/test/article", method = RequestMethod.GET)
    public List<Article> getArticle() {
        return articleService.getAllArticle();
    }

    @RequestMapping(value = "/test/art/insert", method = RequestMethod.POST)
    public String saveArticle(@RequestBody List<Article> list) {
        return articleService.insertArticle(list);
    }

    @RequestMapping(value = "test/delete", method = RequestMethod.GET)
    public String deleteArticle() {
        List<Long> ids = new ArrayList<>();
        ids.add(100L);
        ids.add(101L);
        ids.add(102L);
        ids.add(120L);
        return articleService.deleteArticle(ids);
    }

    @RequestMapping(value = "test/update", method = RequestMethod.GET)
    @InheritableRequest
    public String updateArticle() {
        CompletableFuture.runAsync(() -> articleService.updateArticle());
        System.out.println("running....");
        return "success";
    }

}
