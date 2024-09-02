package com.soguk.soguk.services;

import org.springframework.stereotype.Service;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.repositories.topicRepo;
import com.soguk.soguk.repositories.entryRepo;
import java.util.List;

@Service
public class topicService {


    private final topicRepo topicRepo;

    private final entryRepo entryRepo;

    public topicService(com.soguk.soguk.repositories.topicRepo topicRepo, com.soguk.soguk.repositories.entryRepo entryRepo) {
        this.topicRepo = topicRepo;
        this.entryRepo = entryRepo;
    }

    public Topic createTopic(Topic topic) {
        if (topicRepo.existsByTitle(topic.getTitle())){
            throw new IllegalArgumentException("Bu başlık bulunmaktadır");
        }
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

}
