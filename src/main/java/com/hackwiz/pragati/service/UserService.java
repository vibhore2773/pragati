package com.hackwiz.pragati.service;

import com.hackwiz.pragati.dao.redis.UserDetailsEntity;
import com.hackwiz.pragati.models.requests.LoginRegisterRequest;
import com.hackwiz.pragati.repostitory.redis.UserDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserDetailsRepo userDetailsRepo;

    public UserService(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    public void loginRegisterUser(LoginRegisterRequest loginRegisterRequest) {
        log.info("LoginRegisterService.loginRegisterUser: loginRegisterRequest={}", loginRegisterRequest);

        List<UserDetailsEntity> existingUserList = userDetailsRepo.findByPhoneNumber(loginRegisterRequest.getPhone());
        UserDetailsEntity existingUser = existingUserList.isEmpty() ? null : existingUserList.get(0);
        if (existingUser != null) {
            log.info("LoginRegisterService.loginRegisterUser: User found with phone number={}", loginRegisterRequest.getPhone());
            if(!existingUser.getPassword().equals(loginRegisterRequest.getPassword())) {
                log.error("LoginRegisterService.loginRegisterUser: Password mismatch for phone number={}", loginRegisterRequest.getPhone());
                throw new RuntimeException("Password mismatch for user with phone number=" + loginRegisterRequest.getPhone());
            }
        } else {
            log.info("LoginRegisterService.loginRegisterUser: User not found with phone number={}, registering user", loginRegisterRequest.getPhone());
            UserDetailsEntity newUser = UserDetailsEntity.builder()
                    .name(loginRegisterRequest.getName())
                    .email(loginRegisterRequest.getEmail())
                    .phoneNumber(loginRegisterRequest.getPhone())
                    .userType(loginRegisterRequest.getType())
                    .password(loginRegisterRequest.getPassword())
                    .build();
            userDetailsRepo.save(newUser);
        }
    }


}
