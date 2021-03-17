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

    @PostConstruct
    public void init() {
        News news = new News(1, "新闻", "这是一条新闻", "信息新闻");
        template.save(news);
        SearchHits<News> newses = template.search(Query.findAll(), News.class);
        newses.forEach(doc -> System.out.println(doc));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery("信息", "desc", "context"));
        QueryBuilders.termQuery("context", "这是一条新闻");
        QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("信息", "desc", "context")).must(QueryBuilders.termQuery("context", "这是一条新闻")).should(null);


    }
}
