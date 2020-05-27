package com.scd.mapper;


import com.scd.annotation.DataSourceRouting;
import com.scd.mapper.provider.UserContextProvider;
import com.scd.model.po.TestUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TestUserMapper {
    int insert(TestUser record);

    @DataSourceRouting(dsname = "testds", dsparam = "db-code")
    int insertSelective(TestUser record);

    @SelectProvider(type = UserContextProvider.class, method = "selectUserProvider")
    int selectCountUser(@Param("id") Integer id);

    int insertUserList(List<TestUser> userList);

    List<TestUser> selectUsers(List<Integer> ids);

    List<TestUser> selectResultHandler(List<Integer> ids, RowBounds rowBounds);
}