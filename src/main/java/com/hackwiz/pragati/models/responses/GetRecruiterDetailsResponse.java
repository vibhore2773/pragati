package com.hackwiz.pragati.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import com.hackwiz.pragati.dao.redis.ProfessionalDetails;
import com.hackwiz.pragati.dao.redis.UserDetailsEntity;
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
public class GetRecruiterDetailsResponse {

    private UserType userType;
    private String userId;
    private String name;
    private Address address;
    private String profilePic;
    private List<JobDetailsView> jobDetails;
    private List<ProfessionalDetails> professionalDetailsList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JobDetailsView extends JobDetailsEntity {
        private List<UserDetailsEntity> userDetailsEntityList;
    }
}
