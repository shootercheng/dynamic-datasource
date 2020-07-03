package com.scd.mapper;

import com.scd.annotation.DataSourceRouting;
import com.scd.model.po.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DataSourceRouting(dsname = "testds")
public interface ArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> selectAll();

    int insertList(@Param(value = "list") List<Article> list);

    int deleteByIds(@Param(value = "list") List<Long> list);

    int updateList(@Param("list") List<Article> list);
}