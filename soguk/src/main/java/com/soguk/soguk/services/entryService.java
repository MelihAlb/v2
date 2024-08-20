package com.soguk.soguk.services;

import com.soguk.soguk.models.Entry;
import com.soguk.soguk.repositories.entryRepo;
import com.soguk.soguk.repositories.topicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class entryService {

    @Autowired
    private entryRepo entryRepository;

    @Autowired
    private topicRepo topicRepository;

    public Entry getEntryById(String id) {
        return entryRepository.findById(id).orElse(null);
    }

    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    public Entry createEntry(Entry entry) {
        if (topicRepository.existsById(entry.getTopicId())) {
            topicService.incrementEntryCount(entry.getTopicId());
            return entryRepository.save(entry);
        } else {
            throw new IllegalArgumentException("Başlık bulunamadı");
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
