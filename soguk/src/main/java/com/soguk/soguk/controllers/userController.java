package com.soguk.soguk.controllers;

import com.mongodb.lang.NonNull;
import com.soguk.soguk.models.User;
import com.soguk.soguk.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class userController {

    private final userService userService;

    @Autowired
    public userController(userService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody userLoginRequest loginRequest) {
        User user = userService.findByNick(loginRequest.getNick());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok("Giriş yapıldı");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz kullanıcı adı veya şifre");
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
}
