package com.soguk.soguk.services;

import com.soguk.soguk.models.Entry;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.repositories.entryRepo;
import com.soguk.soguk.repositories.topicRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class entryService {
    private final entryRepo entryRepository;
    private final topicRepo topicRepository;
    public entryService(entryRepo entryRepository, topicRepo topicRepository) {
        this.entryRepository = entryRepository;
        this.topicRepository = topicRepository;
    }

    public Entry getEntryById(String id) {
        return entryRepository.findById(id).orElse(null);
    }

    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    public Entry createEntry(Entry entry) {
        if (topicRepository.existsById(entry.getTopicId())) {
            updateEntryCount(entry.getTopicId());
            return entryRepository.save(entry);
        } else {
            throw new IllegalArgumentException("Başlık bulunamadı");
        }
    }
    private void updateEntryCount(String topicId) {
        int count = entryRepository.countByTopicId(topicId);
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null) {
            topic.setEntryCount(count);
            topicRepository.save(topic);
        }
    }

    public Entry updateEntry(Entry entry) {
        return entryRepository.save(entry);
    }

    public void deleteEntry(String id) {
        entryRepository.deleteById(id);
    }

    public Entry likeEntry(String id) {
        Entry entry = entryRepository.findById(id).orElse(null);
        if (entry != null) {
            entry.setLikeCount(entry.getLikeCount() + 1);
            entryRepository.save(entry);
        }
        return entry;
    }

    public List<Entry> getEntriesByTopicId(String topicId) {
        return entryRepository.findByTopicId(topicId);
    }

    public Entry findById(String id) {
       return entryRepository.findById(id).orElse(null);
    }
}
