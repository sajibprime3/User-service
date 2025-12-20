package com.dark.user.controller.api;

import com.dark.user.dto.AuthRequest;
import com.dark.user.entity.User;
import com.dark.user.util.JwtUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${jwt.secret}")
    String secret;

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            log.info("Authentication: {}", authentication);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(jwtUtil.generateTokenWithRole(user.getId().toString(), user.getRoles()));
    }
}
