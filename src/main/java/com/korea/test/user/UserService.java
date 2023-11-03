package com.korea.test.user;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(String userId, String email, String nickname, String password) {
        User user = new User();
        user.setUserID(userId);
        user.setEmail(email);
        user.setNickname(nickname);
//        user.setPassword(passwordEncoder.encode(password));
        user.setCreateDate(LocalDateTime.now());
        this.userRepository.save(user);
        return user;
    }
}