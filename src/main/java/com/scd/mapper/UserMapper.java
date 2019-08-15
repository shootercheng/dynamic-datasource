package com.scd.mapper;

import com.scd.annotation.DataSourceRouting;
import com.scd.model.po.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author chengdu
 * @date 2019/8/15.
 */
// dsname 与 yml 配置中数据源名字一样， dsparam 为数据库拼接替换部分
@DataSourceRouting(dsname = "testds", dsparam = "db-code")
public interface UserMapper {

    List<User> getAll();

    @DataSourceRouting(dsname = "devds", dsparam = "db-code")
    User getOne(Long id);


    @Select("SELECT count(1) from users")
    int countNum();
}
