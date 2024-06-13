package com.hackwiz.pragati.dao.redis;

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
@RedisHash("professional_detail_job_detail_map")
public class ProfessionalDetailJobDetailMapEntity {
    @Id
    private String id;
    @Indexed
    private String jobDetailId;
    @Indexed
    private String professionalDetailId;
}
