package com.hackwiz.pragati.dao.redis;

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
@RedisHash("professional_detail_job_detail_map")
public class ProfessionalDetailJobDetailMapEntity {
    @Id
    private long id;
    private long jobDetailId;
    private long professionalDetailId;
}
