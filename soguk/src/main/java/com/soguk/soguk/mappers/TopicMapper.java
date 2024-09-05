package com.soguk.soguk.mappers;

import com.soguk.soguk.DTO.TopicDTO;
import com.soguk.soguk.models.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface TopicMapper {
    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    TopicDTO toDTO(Topic topic);
    Topic toEntity(TopicDTO topicDTO);


}
