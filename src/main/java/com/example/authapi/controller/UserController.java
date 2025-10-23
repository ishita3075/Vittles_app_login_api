package com.example.authapi.controller;

import com.example.authapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    // 🔒 Protected route
    @GetMapping("/me")
    public Map<String, String> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "
        String email = jwtUtil.extractEmail(token);

        Map<String, String> response = new HashMap<>();
        response.put("email", email);
        response.put("message", "Token is valid and this is a protected route.");
        return response;
    }
}
