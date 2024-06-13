package com.hackwiz.pragati.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackwiz.pragati.confluent.Producer;
import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import com.hackwiz.pragati.enums.JobStatus;
import com.hackwiz.pragati.models.requests.PostJobRequest;
import com.hackwiz.pragati.models.responses.PostJobResponse;
import com.hackwiz.pragati.models.responses.Timeline;
import com.hackwiz.pragati.repostitory.redis.JobDetailsEntityRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecruiterService {

    private final JobDetailsEntityRepo jobDetailsEntityRepo;
    private final Producer producer;

    private final ObjectMapper objectMapper;

    public RecruiterService(RedisHelperService redisHelperService, JobDetailsEntityRepo jobDetailsEntityRepo, Producer producer, ObjectMapper objectMapper) {
        this.jobDetailsEntityRepo = jobDetailsEntityRepo;
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    public PostJobResponse postJob(PostJobRequest request, String recruiterId) throws JsonProcessingException {
        if (validRequest(request)) {
            String jobId = getPostJobId(request, recruiterId);
            JobDetailsEntity jobDetailsEntity = JobDetailsEntity.builder()
                    .id(jobId)
                    .jobStatus(JobStatus.INITIATED)
                    .recruiterDetailsId(Long.parseLong(recruiterId))
                    .staticSkill(request.getJobType())
                    .timeline(Timeline.builder()
                            .endDate(request.getEndDate())
                            .startDate(request.getStartDate()).build())
                    .rate(request.getMaxRate())
                    .active(true)
                    .address(request.getAddress())
                    .requiredProfessionals(request.getRequiredProfessionals())
                    .build();
            jobDetailsEntityRepo.save(jobDetailsEntity);

            producer.sendMessage(recruiterId, objectMapper.writeValueAsString(jobDetailsEntity));
            return PostJobResponse.builder()
                    .jobId(jobId)
                    .message("Job posted successfully")
                    .success(true)
                    .build();
        }
        return PostJobResponse.builder()
                .message("Invalid request")
                .success(false)
                .build();
    }

    private String getPostJobId(PostJobRequest request, String recruiterId) {
        return recruiterId + "_" + request.getJobType().name() + UUID.randomUUID();
    }

    private boolean validRequest(PostJobRequest request) {
        return request != null && request.getJobType() != null && request.getRequiredProfessionals() > 0;
    }


}
