package com.hackwiz.pragati.dao.redis;

import com.hackwiz.pragati.enums.JobStatus;
import com.hackwiz.pragati.enums.Skill;
import com.hackwiz.pragati.models.Address;
import com.hackwiz.pragati.models.responses.Timeline;
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
@RedisHash("job_details")
public class JobDetailsEntity {

    @Id
    private String id;
    @Indexed
    private long recruiterDetailsId;
    private Skill skill;
    private float rate;
    private JobStatus jobStatus;
    private Address address;
    private boolean active;
    private Timeline timeline;
    private Integer requiredProfessionals;
}
