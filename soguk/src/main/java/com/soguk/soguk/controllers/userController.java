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
    @Autowired
    private  userService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public userController(userService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody userLoginRequest loginRequest) {
        System.out.println("Login attempt: " + loginRequest.getNick());
        User user = userService.findByNick(loginRequest.getNick());

        if (user == null) {
            System.out.println("User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz kullanıcı adı veya şifre");
        }

        System.out.println("User found, validating password");
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            System.out.println("Password mismatch");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz kullanıcı adı veya şifre");
        }

        String token = jwtUtil.generateToken(user.getNick());
        return ResponseEntity.ok(new JwtResponse(token));
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
