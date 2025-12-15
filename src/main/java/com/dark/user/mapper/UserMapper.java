package com.dark.user.mapper;

import com.dark.user.dto.UserDto;
import com.dark.user.dto.UserRegistrationRequest;
import com.dark.user.dto.UserUpdateRequest;
import com.dark.user.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);

    @Mapping(target = "id", ignore = true)
    User userFromRequest(UserUpdateRequest request);

    @Mapping(target = "id", ignore = true)
    User userFromRequest(UserRegistrationRequest request);
}
