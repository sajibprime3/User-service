package com.dark.user.service;

import java.util.List;
import java.util.UUID;

import com.dark.user.dto.UserDto;
import com.dark.user.dto.UserRegistrationRequest;
import com.dark.user.dto.UserUpdateRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();

    UserDto getUserByUsername(String username);

    UserDto updateUser(UUID uuid, UserUpdateRequest request);

    UserDto registerUser(UserRegistrationRequest request);
}
