package com.soguk.soguk.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.soguk.soguk.models.Topic;

public interface topicRepo extends MongoRepository<Topic, String> {
    Topic findByTitle(String title);
    boolean existsByTitle(String title);
}
