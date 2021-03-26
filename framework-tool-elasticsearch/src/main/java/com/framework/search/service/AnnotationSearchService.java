package com.framework.search.service;

import com.framework.search.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AnnotationSearchService {

    @Autowired
    private NewsRepository newsRepository;

    Page<News> findByContext(String context){
        Pageable pageable= PageRequest.of(0, 20);
        return newsRepository.findByContext(context,pageable);
    }

    @PostConstruct
    public void init(){
        Page<News> newsPage = findByContext("新闻");

        System.out.println(newsPage);
    }

}
