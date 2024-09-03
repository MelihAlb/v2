package com.soguk.soguk.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String nick;
    @Indexed(unique = true)
    private String email;
    private String password;
    @CreatedDate
    @Field(name = "createdAt")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Field(name = "updatedAt")
    private LocalDateTime updatedAt;
    @Field("liked_entries")
    private List<Entry> likedEntries;
}