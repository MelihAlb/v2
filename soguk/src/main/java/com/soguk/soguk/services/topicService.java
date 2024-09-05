package com.soguk.soguk.services;

import com.soguk.soguk.DTO.TopicDTO;
import com.soguk.soguk.mappers.TopicMapper;
import com.soguk.soguk.utils.JwtUtil;
import org.springframework.stereotype.Service;
import com.soguk.soguk.models.Topic;
import com.soguk.soguk.repositories.topicRepo;
import com.soguk.soguk.repositories.entryRepo;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class topicService {

    private final TopicMapper topicMapper = TopicMapper.INSTANCE;

    private final topicRepo topicRepo;
    private final entryRepo entryRepo;
    private final JwtUtil jwtUtil;

    public topicService(topicRepo topicRepo, entryRepo entryRepo, JwtUtil jwtUtil) {
        this.topicRepo = topicRepo;
        this.entryRepo = entryRepo;
        this.jwtUtil=jwtUtil;
    }

    public TopicDTO createTopic(String token, TopicDTO topicDTO) {
        if (topicRepo.existsByTitle(topicDTO.getTitle())) {
            throw new IllegalArgumentException("Bu başlık bulunmaktadır");
        }
        String nick = jwtUtil.extractUsername(token);
        Topic topic = topicMapper.toEntity(topicDTO);
        topic.setCreatorId(nick);
        Topic createdTopic = topicRepo.save(topic);
        return topicMapper.toDTO(createdTopic);
    }
    public TopicDTO getTopicByTitle(String title) {
        Topic topic = topicRepo.findByTitle(title);
        return topicMapper.toDTO(topic);
    }

    public List<TopicDTO> getAllTopics() {
        return topicRepo.findAll().stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TopicDTO> getTopicsByCreatorId(String creatorId) {
        return topicRepo.findByCreatorId(creatorId).stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());
    }
}
