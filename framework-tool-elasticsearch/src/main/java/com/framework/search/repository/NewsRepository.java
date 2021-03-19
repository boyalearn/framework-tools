package com.framework.search.repository;

import com.framework.search.service.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NewsRepository extends ElasticsearchRepository<News, Long> {

    @Query("{\"bool\":{\"must\":{\"match\":{\"context\":\"?0\"}}}}")
    Page<News> findByContext(String context, Pageable pageable);
}
