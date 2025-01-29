package com.example.API_Gateway.service;

import com.example.API_Gateway.repository.UserRepository;
import com.example.API_Gateway.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity findUserByUsername(String username){
        log.info("start find user by username");
        return userRepository.findById(username).orElse(null);
    }

    public boolean validatePassword(UserEntity userEntity, String password){
        if (passwordEncoder.matches(password, userEntity.getPassword())){
            log.info("password match");
            return true;
        }
        return false;
    }

    public void saveNewUser(String username, String password){
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(userEntity);

    }
}
