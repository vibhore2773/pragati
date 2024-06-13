package com.hackwiz.pragati.models.producerRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hackwiz.pragati.enums.Skill;
import com.hackwiz.pragati.models.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostJobAsyncRequest {

    private String recruiterId;
    private Skill jobType;
    private String startDate;
    private String endDate;
    private Address address;
    private long maxRate;

}