package com.yusufbudak.habittracker.Auth.Service;

import com.yusufbudak.habittracker.Auth.Model.AuthResponse;
import com.yusufbudak.habittracker.Auth.Model.LoginRequest;
import com.yusufbudak.habittracker.Auth.Model.RegisterRequest;
import com.yusufbudak.habittracker.Domain.UserEntity;
import com.yusufbudak.habittracker.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        UserEntity user = userService.register(
                request.getEmail(),
                request.getPassword(),
                request.getDisplayName()
        );

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}