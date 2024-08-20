package com.soguk.soguk.services;

import com.soguk.soguk.models.User;
import com.soguk.soguk.repositories.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userService {

    @Autowired
    private userRepo userRepo;

    public User registerUser(User user) {
        if (userRepo.existsByNick(user.getNick())) {
            throw new IllegalArgumentException("Nick alınmış durumda");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email alınmış durumda");
        }
        return userRepo.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    public User updateUser(User user) {
        if (userRepo.existsById(user.getId())) {
            return userRepo.save(user);
        }
        return null;
    }

    public User findByNick(String nick) {
        return userRepo.findByNick(nick);
    }
}
