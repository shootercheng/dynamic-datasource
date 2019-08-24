package com.scd.service.impl;

import com.scd.mapper.TaskParamMapper;
import com.scd.model.po.TaskParam;
import com.scd.service.TaskParamService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chengdu
 * @date 2019/8/24.
 */
@Service
public class TaskParamServiceImpl implements TaskParamService {

    @Autowired
    private TaskParamMapper taskParamMapper;


    @Override
    public int saveTaskParam(TaskParam taskParam) {
        return taskParamMapper.insertSelective(taskParam);
    }

    @Override
    public String queryScheduleEx(Integer id) {
        TaskParam taskParam = taskParamMapper.selectByPrimaryKey(id);
        if (taskParam != null){
            return taskParam.getParamValue();
        }
        return "";
    }
}
