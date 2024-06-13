package com.hackwiz.pragati.dao.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("recruiter_details")
public class RecruiterDetailsEntity {

    @Id
    private long id;
    @Indexed
    private long userId;
    private Float rating;
    private PersonalDetails personalDetails;


    private static class PersonalDetails {
        private String profilePicture;
    }
}
