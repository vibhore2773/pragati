package com.hackwiz.pragati.dao.redis;

import com.hackwiz.pragati.enums.JobStatus;
import com.hackwiz.pragati.enums.Skill;
import com.hackwiz.pragati.models.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("job_details")
public class JobDetailsEntity {

    @Id
    private long id;
    private long recruiterDetailsId;
    private Skill skill;
    private float rate;
    private JobStatus jobStatus;
    private Address address;
    private boolean active;

}
