package com.hackwiz.pragati.dao.redis;

import com.hackwiz.pragati.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("user_details")
public class UserDetailsEntity {


    @Id
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private String password;


}
