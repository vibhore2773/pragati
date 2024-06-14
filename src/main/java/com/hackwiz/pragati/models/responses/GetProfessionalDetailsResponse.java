package com.hackwiz.pragati.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import com.hackwiz.pragati.enums.UserType;
import com.hackwiz.pragati.models.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetProfessionalDetailsResponse {

    private UserType userType;
    private String userId;
    private String name;
    private List<SkillResponse> skills;
    private Address address;
    private String profilePic;
    private boolean kycVerified;
    private List<JobDetailsEntity> jobDetails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillResponse {
        private String skill;
        private int experience;
        private float rate;
    }

}
