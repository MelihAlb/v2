package com.soguk.soguk.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.services.topicService;
import java.util.List;

@RestController
@RequestMapping("/topics")
@CrossOrigin(origins = "http://localhost:3000")
public class topicController {

    private topicService topicService;
    public topicController(com.soguk.soguk.services.topicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/title/{title}")
    public Topic getTopicByTitle(@PathVariable String title) {
        return topicService.getTopicByTitle(title);
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }
    @PostMapping("post")
    public ResponseEntity<Topic> createTopic(@RequestHeader("Authorization") String authHeader, @RequestBody Topic topic) {
        // Authorization başlığından token'ı al
        String token = authHeader.replace("Bearer ", "");

        // Topic'i oluştur
        Topic createdTopic = topicService.createTopic(token, topic);

        return ResponseEntity.ok(createdTopic);
    }
    @GetMapping("/by/{creatorId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Topic>> getTopicsByCreatorId(@PathVariable String creatorId) {
        List<Topic> topics = topicService.getTopicsByCreatorId(creatorId);
        return ResponseEntity.ok(topics);
    }

}
