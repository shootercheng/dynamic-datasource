package com.scd.mapper;


import com.scd.annotation.DataSourceRouting;
import com.scd.mapper.provider.UserContextProvider;
import com.scd.model.po.TestUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface TestUserMapper {
    int insert(TestUser record);

    @DataSourceRouting(dsname = "testds", dsparam = "db-code")
    int insertSelective(TestUser record);

    @SelectProvider(type = UserContextProvider.class, method = "selectUserProvider")
    int selectCountUser(@Param("id") Integer id);
}