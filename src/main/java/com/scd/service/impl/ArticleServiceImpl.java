package com.scd.service.impl;

import com.scd.mapper.ArticleMapper;
import com.scd.model.po.Article;
import com.scd.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengdu
 * @date 2019/11/24
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getAllArticle() {
        LOGGER.info("query all article");
        return articleMapper.selectAll();
    }

    @Override
    public String insertArticle(List<Article> articleList) {
        int inserted = articleMapper.insertList(articleList);
        return "insert " + inserted + " records";
    }

    @Override
    public String deleteArticle(List<Long> ids) {
        int deleted = articleMapper.deleteByIds(ids);
        return "delete " + deleted + " records";
    }

    @Override
    public String updateArticle() {
        // 模拟异步返回结果操作
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Article> articles = articleMapper.selectAll();
        if (articles.size() > 2) {
           articles = articles.subList(0, 2);
        }
        int update = articleMapper.updateList(articles);
        return "updated " + update + "records";
    }
}
