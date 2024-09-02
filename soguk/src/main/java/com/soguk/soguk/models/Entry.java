package com.soguk.soguk.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Setter
@Getter
@Document
public class Entry {
    @Id
    private String id;
    private String content;
    private int likeCount = 0;  // Varsayılan değer olarak 0
    private String topicId;  // Başlık ile ilişkilendirme
    public Object getAuthorNick() {
        return null;
    }
}
