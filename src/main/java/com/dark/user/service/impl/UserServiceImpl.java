package com.dark.user.service.impl;

import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;

import com.dark.user.dto.UserDto;
import com.dark.user.dto.UserRegistrationRequest;
import com.dark.user.dto.UserUpdateRequest;
import com.dark.user.entity.User;
import com.dark.user.mapper.UserMapper;
import com.dark.user.repository.UserRepository;
import com.dark.user.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow();
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::userToUserDto).toList();
    }

    @Override
    public UserDto getUserByUsername(String username) {
        // No error handling. :(
        User user = userRepository.findUserByUsername(username).orElseThrow();
        return userMapper.userToUserDto(user);
    }

    @Override
    public User getUserByUUID(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow();
        return user;
    }

    @Override
    public UserDto getAuthenticatedUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return null;
        }
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(UUID uuid, UserUpdateRequest request) {
        User user = userRepository.findById(uuid).orElseThrow();
        user.setEmail(request.getEmail());
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserDto registerUser(UserRegistrationRequest request) {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            // TODO: use custom exceptions and handle them accordingly.
            throw new RuntimeException("User already exists.");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of("user")) // Default Role for everyone.
                .build();
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

}
