package com.scd.mapper;


import com.scd.model.po.TaskParam;

import java.util.List;

public interface TaskParamMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TaskParam record);

    int insertSelective(TaskParam record);

    TaskParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskParam record);

    int updateByPrimaryKey(TaskParam record);

    List<TaskParam> selectAllTask();

    List<String[]> selectArrRes();

    int insertBatch(List<TaskParam> taskParamList);

    List<TaskParam> selectByTaskParam(TaskParam taskParam);
}