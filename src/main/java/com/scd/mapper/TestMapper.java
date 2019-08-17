package com.scd.mapper;


import com.scd.annotation.DataSourceRouting;
import com.scd.model.po.Test;

public interface TestMapper {

    int deleteByPrimaryKey(Long id);

    @DataSourceRouting(dsname = "testds", dsparam = "db-code")
    int insert(Test record);

    @DataSourceRouting(dsname = "devds", dsparam = "db-code")
    int insertSelective(Test record);

    Test selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);
}