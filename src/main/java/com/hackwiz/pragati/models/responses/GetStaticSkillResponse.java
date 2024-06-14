package com.hackwiz.pragati.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetStaticSkillResponse {
    List<Skill> skills;

    @Data
    @Builder
    public static class Skill {
        private String skillName;
        private String imageUrl;
        private float rate;
    }
}
