package com.soguk.soguk.DTO;

import com.soguk.soguk.models.Entry;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDetailsResponse {
    private final User user;
    private final List<Entry> likedEntries;
    private final List<Topic> createdTopics;
    private final List<Entry> createdEntries;

    public UserDetailsResponse(User user, List<Entry> likedEntries, List<Topic> createdTopics, List<Entry> createdEntries) {
        this.user = user;
        this.likedEntries = likedEntries;
        this.createdTopics = createdTopics;
        this.createdEntries = createdEntries;
    }
}
