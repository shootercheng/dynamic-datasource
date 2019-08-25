package com.scd.controller;

import com.scd.model.po.TaskParam;
import com.scd.service.DataService;
import com.scd.service.TaskParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * @author chengdu
 * @date 2019/8/24.
 */
@RestController
public class TaskController {

    @Autowired
    private TaskParamService taskParamService;

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/task/param", method = RequestMethod.POST)
    public String saveTaskParam(@RequestBody  TaskParam taskParam, HttpServletRequest request){
        String header = request.getHeader("id");
        taskParamService.saveTaskParam(taskParam);
        return "success";
    }

    @RequestMapping(value = "/task/execute", method = RequestMethod.GET)
    public String executeTask(){
        CompletableFuture.runAsync(() -> dataService.doHandler());
        return "success";
    }
}
