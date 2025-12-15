package com.dark.user.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    UUID id;
    String username;
    String email;
}
