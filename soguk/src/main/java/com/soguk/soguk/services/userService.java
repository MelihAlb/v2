package com.soguk.soguk.services;

import com.soguk.soguk.models.User;
import com.soguk.soguk.repositories.userRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userService {

    private final userRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public userService(com.soguk.soguk.repositories.userRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
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

}
