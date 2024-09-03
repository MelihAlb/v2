package com.soguk.soguk.services;

import com.soguk.soguk.utils.JwtUtil;
import org.springframework.stereotype.Service;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.repositories.topicRepo;
import com.soguk.soguk.repositories.entryRepo;
import java.util.List;

@Service
public class topicService {


    private final topicRepo topicRepo;
    private final entryRepo entryRepo;
    private final JwtUtil jwtUtil;

    public topicService(topicRepo topicRepo, entryRepo entryRepo, JwtUtil jwtUtil) {
        this.topicRepo = topicRepo;
        this.entryRepo = entryRepo;
        this.jwtUtil=jwtUtil;
    }

    public Topic createTopic(String token, Topic topic) {
        if (topicRepo.existsByTitle(topic.getTitle())) {
            throw new IllegalArgumentException("Bu başlık bulunmaktadır");
        }
        String nick = jwtUtil.extractUsername(token);
        topic.setCreatorId(nick);
        return topicRepo.save(topic);
    }
    public void updateEntryCount(String topicId) {
        int count = entryRepo.countByTopicId(topicId);
        Topic topic = topicRepo.findById(topicId).orElse(null);
        if (topic != null) {
            topic.setEntryCount(count);
            topicRepo.save(topic);
        }
    }

    public Topic getTopicByTitle(String title) {
        return topicRepo.findByTitle(title);
    }

    public List<Topic> getAllTopics() {
        return topicRepo.findAll();
    }

    public List<Topic> getTopicsByCreatorId(String creatorId) {
        return topicRepo.findByCreatorId(creatorId);
    }
}
