package com.soguk.soguk.services;

import com.soguk.soguk.models.Entry;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.models.User;
import com.soguk.soguk.repositories.entryRepo;
import com.soguk.soguk.repositories.topicRepo;
import com.soguk.soguk.repositories.userRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class userService {
    private final entryRepo entryRepo;
    private final userRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final topicRepo topicRepo;

    public userService(userRepo userRepo, PasswordEncoder passwordEncoder, entryRepo entryRepo,topicRepo topicRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.entryRepo = entryRepo;
        this.topicRepo=topicRepo;
    }

    public User registerUser(User user) {
        if (userRepo.existsByNick(user.getNick())) {
            throw new IllegalArgumentException("Nick alınmış durumda");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email alınmış durumda");
        }
        /*String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
         */
        return userRepo.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    public User updateUser(User user) {
        if (userRepo.existsById(user.getId())) {

            User existingUser = userRepo.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı"));
            user.setPassword(existingUser.getPassword());
            return userRepo.save(user);
        }
        return null;
    }

    public User findByNick(String nick) {
        return userRepo.findByNick(nick);
    }

    public List<String> getAllUserNicks() {
        return userRepo.findAll().stream()
                .map(User::getNick)
                .collect(Collectors.toList());
    }
    public List<Entry> getLikedEntries(String userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            return user.getLikedEntries();
        }
        return List.of();
    }
    public List<Topic> getCreatedTopics(String userId) { // Creator ID'ye göre topic'leri bul
        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            return topicRepo.findByCreatorId(userId);
        }
        return List.of();
    }

    public List<Entry> getCreatedEntries(String userId) {  // Author ID'ye göre entry'leri bul
        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            return entryRepo.findByAuthorId(userId);
        }
        return List.of();
    }

}
