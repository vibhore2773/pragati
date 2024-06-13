package com.hackwiz.pragati.dao.redis;


import com.hackwiz.pragati.enums.Skill;
import com.hackwiz.pragati.enums.StaticSkill;
import com.hackwiz.pragati.enums.StaticSkill;
import com.hackwiz.pragati.models.Address;
import com.hackwiz.pragati.models.responses.Timeline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("professional_details")
public class ProfessionalDetails {

    @Id
    private long id;
    private long userId;
    private Address address;
    private String qualification;
    private List<Skill> skills;
    private Timeline availability;
    private PersonalDetails personalDetails;
    private List<AssignedJobs> assignedJobs;
    private boolean kycVerified;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PersonalDetails {
        private String profilePicture;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class AssignedJobs {
        private String jobIds;
        private String startDate;
        private String endDate;
    }
}
