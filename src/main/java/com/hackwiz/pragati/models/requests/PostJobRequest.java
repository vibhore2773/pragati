package com.hackwiz.pragati.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hackwiz.pragati.enums.StaticSkill;
import com.hackwiz.pragati.models.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostJobRequest {
    private StaticSkill jobType;
    private Date startDate;
    private Date endDate;
    private Address address;
    private long maxRate;
    private int requiredProfessionals;
}
