package com.soguk.soguk.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document
public class Entry {
    @Id
    private String id;
    private String content;
    private int likeCount = 0;  // Varsayılan değer olarak 0
    private String topicId;  // Başlık ile ilişkilendirme
    @Field("likedBy")
    private List<String> likedBy = new ArrayList<>();
    private String authorId;
    private String creatorId;

    public Object getAuthorNick() {
        return null;
    }
}
