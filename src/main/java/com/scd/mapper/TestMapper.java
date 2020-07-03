package com.scd.mapper;


import com.scd.annotation.DataSourceRouting;
import com.scd.model.po.Test;

public interface TestMapper {

    int deleteByPrimaryKey(Long id);

    @DataSourceRouting(dsname = "testds")
    int insert(Test record);

    @DataSourceRouting(dsname = "devds")
    int insertSelective(Test record);

    Test selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);
}