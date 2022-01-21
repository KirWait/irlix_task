package com.irlix.task.mapper;

import com.irlix.task.dto.UserRequestDto;
import com.irlix.task.dto.UserResponseDto;
import com.irlix.task.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDto userEntityToUserResponseDTO(UserEntity userEntity);
    UserEntity userRequestDTOToUserEntity(UserRequestDto requestDto);
}
