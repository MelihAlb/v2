package com.soguk.soguk.DTO;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class TopicDTO {
    private String id;
    private String title;
    private Date createdAt;
    private Date updatedAt;
    private int entryCount;
    private String creatorId;
}
