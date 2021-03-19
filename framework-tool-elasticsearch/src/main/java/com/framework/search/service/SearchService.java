package com.framework.search.service;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SearchService {
    @Autowired
    ElasticsearchRestTemplate template;

    //@PostConstruct
    public void init() {
        News news = new News(1, "新闻", "这是一条新闻", "信息新闻");
        template.save(news);
        SearchHits<News> newses = template.search(Query.findAll(), News.class);
        newses.forEach(doc -> System.out.println(doc));

        //**模糊匹配“信息”关键词在desc、context字段中
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery("信息", "desc", "context"));
        //**精确匹配“这是一条新闻”在context字段中
        QueryBuilders.termQuery("context", "这是一条新闻");
        /**
         *
         * 布尔查询
         * must 表示 并且
         * not_must 表示 非
         * should 表示 或者
         *
         */
        QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("信息", "desc", "context")).must(QueryBuilders.termQuery("context", "这是一条新闻"));

    }
}
