package com.scd.mapper;

import com.scd.annotation.DataSourceRouting;
import com.scd.mapper.provider.UserContextProvider;
import com.scd.model.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author chengdu
 * @date 2019/8/15.
 */
@DataSourceRouting(dsname = "testds")
public interface UserMapper {

    List<User> getAll();

    @DataSourceRouting(dsname = "devds")
    User getOne(Long id);


    @Select("SELECT count(1) from users")
    int countNum();
}
