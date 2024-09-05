package com.soguk.soguk.controllers;


import com.soguk.soguk.DTO.TopicDTO;
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
    public ResponseEntity<TopicDTO> getTopicByTitle(@PathVariable String title) {
        TopicDTO topicDTO = topicService.getTopicByTitle(title);
        return ResponseEntity.ok(topicDTO);
    }

    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        List<TopicDTO> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("post")
    public ResponseEntity<TopicDTO> createTopic(@RequestHeader("Authorization") String authHeader, @RequestBody TopicDTO topicDTO) {
        String token = authHeader.replace("Bearer ", "");
        TopicDTO createdTopic = topicService.createTopic(token, topicDTO);
        return ResponseEntity.ok(createdTopic);
    }
    @GetMapping("/by/{creatorId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TopicDTO>> getTopicsByCreatorId(@PathVariable String creatorId) {
        List<TopicDTO> topics = topicService.getTopicsByCreatorId(creatorId);
        return ResponseEntity.ok(topics);
    }

}
