package com.soguk.soguk.mappers;

import com.soguk.soguk.DTO.entryDTO;
import com.soguk.soguk.models.Entry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntryMapper {
    EntryMapper Instance = Mappers.getMapper(EntryMapper.class);
    @Mapping(source = "content",target = "content")
    entryDTO toDTO (Entry entry);
}
