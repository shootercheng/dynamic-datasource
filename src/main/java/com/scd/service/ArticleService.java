package com.scd.service;

import com.scd.model.po.Article;

import java.util.List;

/**
 * @author chengdu
 * @date 2019/11/24
 */
public interface ArticleService {

    List<Article> getAllArticle();

    String insertArticle(List<Article> articleList);

    String deleteArticle(List<Long> ids);

    String updateArticle();
}
