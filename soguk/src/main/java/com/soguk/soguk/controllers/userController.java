package com.soguk.soguk.controllers;

import com.mongodb.lang.NonNull;
import com.soguk.soguk.DTO.UserDetailsResponse;
import com.soguk.soguk.models.Entry;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.models.User;
import com.soguk.soguk.services.userService;
import com.soguk.soguk.utils.JwtResponse;
import com.soguk.soguk.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class userController {
    private final JwtUtil jwtUtil;
    private  userService userService;
    private PasswordEncoder passwordEncoder;

    public userController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, com.soguk.soguk.services.userService userService) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody userLoginRequest loginRequest) {
        System.out.println("Login attempt for nick: " + loginRequest.getNick());

        User user = userService.findByNick(loginRequest.getNick());

        if (user == null) {
            System.out.println("User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz kullanıcı adı veya şifre");
        }

        System.out.println("User found, validating password for user: " + loginRequest.getNick());
        System.out.println("Password from database: " + user.getPassword()); // Loglama

        if (!loginRequest.getPassword().equals(user.getPassword())) {
            System.out.println("Password mismatch for user: " + loginRequest.getNick());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz kullanıcı adı veya şifre");
        }

        String token = jwtUtil.generateToken(user.getNick());
        System.out.println("Token generated for user: " + loginRequest.getNick() + " - Token: " + token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/search")
    public ResponseEntity<User> searchUser(@RequestParam("query") String query) {
        System.out.println("search işlemine girdi");
        User user = userService.findByNick(query);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @NonNull String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        if (!id.equals(user.getId())) {
            return ResponseEntity.badRequest().build();
        }
        User updatedUser = userService.updateUser(user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/nicks") // yazarları çekmek için
    public ResponseEntity<List<String>> getAllUserNicks() {
        List<String> userNicks = userService.getAllUserNicks();
        return ResponseEntity.ok(userNicks);
    }

    @GetMapping("/username/{username}/id")
    public ResponseEntity<String> getUserIdByUsername(@PathVariable String username) {
        Optional<User> userOptional = Optional.ofNullable(userService.findByNick(username));

        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        return ResponseEntity.ok(user.getId()); // ID'yi döndür
    }
    @GetMapping("/nick/{nick}/details")
    public ResponseEntity<UserDetailsResponse> getUserDetailsByUsername(@PathVariable String nick) {
        Optional<User> userOptional = Optional.ofNullable(userService.findByNick(nick));

        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        List<Entry> likedEntries = userService.getLikedEntries(user.getId());
        List<Topic> createdTopics = userService.getCreatedTopics(user.getId());
        List<Entry> createdEntries = userService.getCreatedEntries(user.getId());
        UserDetailsResponse response = new UserDetailsResponse(user, likedEntries, createdTopics, createdEntries);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/me")// giriş yapmış kullanıcı bilgileri
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
        // Token'ı al ve kullanıcının kimliğini belirle
        String token = request.getHeader("Authorization");
        String nick = jwtUtil.extractUsername(token.replace("Bearer ", ""));

        if (nick != null) {
            User user = userService.findByNick(nick);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
