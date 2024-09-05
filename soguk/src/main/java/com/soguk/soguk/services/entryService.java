package com.soguk.soguk.services;

import com.soguk.soguk.DTO.entryDTO;
import com.soguk.soguk.mappers.EntryMapper;
import com.soguk.soguk.models.Entry;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.models.User;
import com.soguk.soguk.repositories.entryRepo;
import com.soguk.soguk.repositories.topicRepo;
import com.soguk.soguk.repositories.userRepo;
import com.soguk.soguk.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class entryService {
    private final userRepo userRepo;
    private final entryRepo entryRepo;
    private final topicRepo topicRepo;
    private final JwtUtil jwtUtil;

    public entryService(userRepo userRepo,entryRepo entryRepo, topicRepo topicRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.entryRepo = entryRepo;
        this.topicRepo = topicRepo;
        this.jwtUtil = jwtUtil;
    }

    public Entry getEntryById(String id) {
        return entryRepo.findById(id).orElse(null);
    }

    public List<Entry> getAllEntries() {
        return entryRepo.findAll();
    }

    public Entry createEntry(String token, Entry entry) {
        if (topicRepo.existsById(entry.getTopicId())) {
            updateEntryCount(entry.getTopicId());
            String nick = jwtUtil.extractUsername(token);
            System.out.println("Extracted Nick: " + nick);
            entry.setCreatorId(nick);
            return entryRepo.save(entry);
        } else {
            throw new IllegalArgumentException("Başlık bulunamadı");
        }
    }

    private void updateEntryCount(String topicId) {
        int count = entryRepo.countByTopicId(topicId);
        System.out.println("Entry Count for Topic ID " + topicId + ": " + count);
        Topic topic = topicRepo.findById(topicId).orElseThrow(() -> new IllegalArgumentException("Topic not found"));
        topic.setEntryCount(count);
        topicRepo.save(topic);
        System.out.println("Updated Entry Count for Topic ID " + topicId);
    }

    public Entry updateEntry(Entry entry) {
        return entryRepo.save(entry);
    }

    public void deleteEntry(String id) {
        entryRepo.deleteById(id);
    }

    public boolean checkIfUserLiked(String entryId, User user) {
        // Entry nesnesini bul
        Entry entry = entryRepo.findById(entryId).orElseThrow(() -> new IllegalArgumentException("Entry bulunamadı"));

        // Kullanıcının daha önce beğenip beğenmediğini kontrol et
        return entry.getLikedBy().contains(user.getNick());
    }

    public void likeEntry(String entryId, User user) {
        Entry entry = entryRepo.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Entry bulunamadı"));
        if (entry.getLikedBy().contains(user.getNick())) {
            throw new RuntimeException("Entry zaten beğenildi");
        }
        entry.getLikedBy().add(user.getNick());
        entry.setLikeCount(entry.getLikeCount() + 1);
        entryRepo.save(entry);
    }

    public List<Entry> getEntriesByTopicId(String topicId) {
        return entryRepo.findByTopicId(topicId);
    }

    public Entry findById(String id) {
       return entryRepo.findById(id).orElse(null);
    }

    public List<String> getLikesByEntryId(String entryId) {
        Entry entry = entryRepo.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Entry bulunamadı"));
        return new ArrayList<>(entry.getLikedBy());
    }// Belirli bir kullanıcının beğendiği entry'leri döndürür
    public List<entryDTO> getEntriesLikedByUser(String nick) {
        List<Entry> entries = entryRepo.findByLikedByContains(nick);
        return entries.stream()
                .map(EntryMapper.Instance::toDTO)
                .collect(Collectors.toList());
    }
    public List<Entry> getEntriesByCreatorId(String creatorId) {
        return entryRepo.findByCreatorId(creatorId);
    }

}
