package com.soguk.soguk.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Setter
@Getter
@Document(collection = "topics")
public class Topic {

    @Id
    private String id;
    private String title;
    @CreatedDate
    @Field(name = "createdAt")
    private Date createdAt;
    @LastModifiedDate
    @Field(name = "updateAt")
    private Date updatedAt;
    private int entryCount = 0;
}
