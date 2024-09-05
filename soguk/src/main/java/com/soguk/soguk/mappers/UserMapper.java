package com.soguk.soguk.mappers;

import com.soguk.soguk.DTO.UserDTO;
import com.soguk.soguk.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper Instance = Mappers.getMapper(UserMapper.class);
    UserDTO toDTO(User user);
    User toUser(UserDTO userDTO);

}
