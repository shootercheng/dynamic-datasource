package com.scd.mapper;


import com.scd.annotation.DataSourceRouting;
import com.scd.mapper.provider.UserContextProvider;
import com.scd.model.po.TestUser;
import com.scd.model.po.TestUserDateStr;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TestUserMapper {
    int insert(TestUser record);

    @DataSourceRouting(dsname = "testds")
    int insertSelective(TestUser record);

    @SelectProvider(type = UserContextProvider.class, method = "selectUserProvider")
    int selectCountUser(@Param("id") Integer id);

    int insertUserList(List<TestUser> userList);

    int insertUserListManyParam(@Param("list") List<TestUser> userList, @Param("status") String status);

    List<TestUser> selectUsers(List<Integer> ids);

    List<TestUser> selectResultHandler(List<Integer> ids, RowBounds rowBounds);

    int insertLocalDate(TestUser record);

    int insertLocalDateParam(@Param("name") String name, @Param("address") String address,
                             @Param("createTime") Date ctime);

    int insertStrDateParam(@Param("name") String name, @Param("address") String address,
                           @Param("createTime") String ctime);

    TestUserDateStr selectUserById(Integer id);
}