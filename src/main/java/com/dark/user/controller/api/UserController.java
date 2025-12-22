package com.dark.user.controller.api;

import java.util.List;
import java.util.UUID;

import com.dark.user.dto.UserDto;
import com.dark.user.dto.UserRegistrationRequest;
import com.dark.user.dto.UserUpdateRequest;
import com.dark.user.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/u/{username}")
    ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        UserDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    ResponseEntity<UserDto> getSelfInfo(Authentication authentication) {
        UserDto user = userService.getAuthenticatedUser(authentication);
        if (user == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    ResponseEntity<List<UserDto>> getAllUserInfo() {
        List<UserDto> user = userService.getAllUsers();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    ResponseEntity<UserDto> registerUser(@RequestBody UserRegistrationRequest request) {
        UserDto user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{uuid}")
    ResponseEntity<UserDto> updateUser(@PathVariable UUID uuid, @RequestBody UserUpdateRequest request) {
        UserDto updatedUser = userService.updateUser(uuid, request);
        return ResponseEntity.ok(updatedUser);
    }
}
