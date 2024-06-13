package com.hackwiz.pragati.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hackwiz.pragati.enums.Skill;
import com.hackwiz.pragati.models.Address;
import com.hackwiz.pragati.models.responses.Timeline;
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
public class AddSkillsKycRequest {
    private long userId;
    private List<SkillRequest> skillList;
    private Address address;
    private boolean markKyc;
    private Timeline availability;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillRequest {
        private Skill skillName;
        private float rate;
    }
}
