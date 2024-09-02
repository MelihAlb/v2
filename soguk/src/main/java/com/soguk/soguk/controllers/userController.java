package com.soguk.soguk.controllers;

import com.mongodb.lang.NonNull;
import com.soguk.soguk.models.User;
import com.soguk.soguk.services.userService;
import com.soguk.soguk.utils.JwtResponse;
import com.soguk.soguk.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
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
}
