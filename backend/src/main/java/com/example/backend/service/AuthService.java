package com.example.backend.service;

import com.example.backend.dto.AuthResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.SignupRequest;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // SIGNUP
    public String signup(SignupRequest request) {

        // check if email already exists
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        // encrypt password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // every signup becomes MEMBER
        user.setRole("MEMBER");

        userRepository.save(user);

        return "User Registered Successfully";
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        // check role
        if(!user.getRole().equalsIgnoreCase(request.getRole())) {

            throw new RuntimeException("Invalid Role");
        }

        // check password
        boolean isMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if(!isMatch) {

            throw new RuntimeException("Invalid Password");
        }

        // generate JWT token
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}