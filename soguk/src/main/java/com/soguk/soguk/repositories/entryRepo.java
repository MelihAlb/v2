package com.soguk.soguk.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.soguk.soguk.models.Entry;

import java.util.List;

public interface entryRepo extends MongoRepository<Entry, String> {
    List<Entry> findByTopicId(String topicId);
}
