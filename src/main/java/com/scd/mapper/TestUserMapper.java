package com.scd.mapper;


import com.scd.annotation.DataSourceRouting;
import com.scd.model.po.TestUser;

public interface TestUserMapper {
    int insert(TestUser record);

    @DataSourceRouting(dsname = "testds", dsparam = "db-code")
    int insertSelective(TestUser record);
}