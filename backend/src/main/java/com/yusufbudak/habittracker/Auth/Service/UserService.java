package com.yusufbudak.habittracker.Auth.Service;


import com.yusufbudak.habittracker.Domain.UserEntity;
import com.yusufbudak.habittracker.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity  register(String email, String password, String displayName) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity user = UserEntity.builder()
                .email(email)
                .displayName(displayName)
                .passwordHash(passwordEncoder.encode(password))
                .build();

        return userRepository.save(user);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
