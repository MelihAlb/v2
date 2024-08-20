package com.soguk.soguk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.services.topicService;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class topicController {

    @Autowired
    private topicService topicService;


    @GetMapping("/title/{title}")
    public Topic getTopicByTitle(@PathVariable String title) {
        return topicService.getTopicByTitle(title);
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @PostMapping("post")
    public Topic createTopic(@RequestBody Topic topic) {
        return topicService.createTopic(topic);
    }

}
