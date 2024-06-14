package com.hackwiz.pragati.controller;

import com.hackwiz.pragati.enums.StaticSkill;
import com.hackwiz.pragati.models.responses.GetStaticSkillResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/data")
@CrossOrigin(origins = "*")
public class DataController {

    @GetMapping("/skill")
    public ResponseEntity<GetStaticSkillResponse> getSkills() {
        GetStaticSkillResponse response = getSkillFromStaticEnums();
        return ResponseEntity.ok(response);
    }

    private GetStaticSkillResponse getSkillFromStaticEnums() {
        List<GetStaticSkillResponse.Skill> skills = new ArrayList<>();
        for (StaticSkill skill : StaticSkill.values()) {
            skills.add(GetStaticSkillResponse.Skill.builder()
                    .skillName(skill.name())
                    .imageUrl(skill.getImage())
                    .rate(skill.getRate())
                    .build());
        }
        return GetStaticSkillResponse.builder().skills(skills).build();
    }

}
