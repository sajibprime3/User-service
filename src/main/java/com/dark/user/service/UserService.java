package com.dark.user.service;

import java.util.List;
import java.util.UUID;

import com.dark.user.dto.UserDto;
import com.dark.user.dto.UserRegistrationRequest;
import com.dark.user.dto.UserUpdateRequest;
import com.dark.user.entity.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();

    UserDto getUserByUsername(String username);

    User getUserByUUID(UUID uuid);

    UserDto getAuthenticatedUser(Authentication authentication);

    UserDto updateUser(UUID uuid, UserUpdateRequest request);

    UserDto registerUser(UserRegistrationRequest request);
}
