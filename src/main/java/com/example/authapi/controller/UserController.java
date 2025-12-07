package com.example.authapi.controller;

import com.example.authapi.model.User;
import com.example.authapi.repository.UserRepository;
import com.example.authapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // ✅ Protected route: returns USER ID directly from database
    @GetMapping("/me")
    public Map<String, Object> getUserInfo(@RequestHeader("Authorization") String authHeader) {

        // ✅ Remove "Bearer "
        String token = authHeader.substring(7);

        // ✅ Extract EMAIL from JWT
        String email = jwtUtil.extractEmail(token);

        // ✅ Fetch user directly from database
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found for email: " + email);
        }

        User user = optionalUser.get();

        // ✅ Send ID + EMAIL + NAME to frontend
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());        // ✅ DB USER ID
        response.put("email", user.getEmail());
        response.put("name", user.getName());

        return response;
    }
}
