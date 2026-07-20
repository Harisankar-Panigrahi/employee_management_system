package com.ems.auth;

import com.ems.security.JwtService;
import com.ems.user.Role;
import com.ems.user.User;
import com.ems.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        // Step 1: Check if email already exists
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }

        // Step 2: Create User
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        // Step 3: Save User
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        // Step 4: Return Response
        return AuthResponse.builder()
                .message("User Registered Successfully")
                .token(token)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            System.out.println("Authentication Successful");

        } catch (Exception e) {
            e.printStackTrace();   // <-- VERY IMPORTANT
            throw e;
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        System.out.println("Request Password = " + request.getPassword());
        System.out.println("DB Password = " + user.getPassword());
        System.out.println(passwordEncoder.matches(request.getPassword(), user.getPassword()));

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .message("Login successful")
                .token(token)
                .build();
    }
}
