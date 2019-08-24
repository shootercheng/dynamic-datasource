package com.scd.mapper;


import com.scd.model.po.TaskParam;

public interface TaskParamMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TaskParam record);

    int insertSelective(TaskParam record);

    TaskParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskParam record);

    int updateByPrimaryKey(TaskParam record);
}