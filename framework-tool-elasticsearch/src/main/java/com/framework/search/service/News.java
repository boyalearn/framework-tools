package com.framework.search.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@Setter
@Document(indexName = "news")
@ToString
@AllArgsConstructor
public class News {
    @Id
    private int id;
    @Field
    private String  name;
    @Field
    private String context;
    @Field
    private String desc;
}
