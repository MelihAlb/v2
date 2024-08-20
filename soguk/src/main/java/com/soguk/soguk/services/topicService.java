package com.soguk.soguk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.repositories.topicRepo;

import java.util.List;

@Service
public class topicService {

    @Autowired
    private static topicRepo topicRepo;
    public Topic createTopic(Topic topic) {
        if (topicRepo.existsByTitle(topic.getTitle())){
            throw new IllegalArgumentException("Bu başlık bulunmaktadır");
        }
        return topicRepo.save(topic);
    }
    public static void incrementEntryCount(String topicId) {
        Topic topic = topicRepo.findById(topicId).orElse(null);
        if (topic != null) {
            topic.setEntryCount(topic.getEntryCount() + 1);
            topicRepo.save(topic);
        }
    }
    public Topic getTopicById(String id) {
        return topicRepo.findById(id).orElse(null);
    }

    public Topic getTopicByTitle(String title) {
        return topicRepo.findByTitle(title);
    }

    public List<Topic> getAllTopics() {
        return topicRepo.findAll();
    }
    public Topic updateTopic(Topic topic) {
        return topicRepo.save(topic);
    }

    public void deleteTopic(String id) {
        topicRepo.deleteById(id);
    }
}
