package com.hackwiz.pragati.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackwiz.pragati.confluent.Producer;
import com.hackwiz.pragati.models.producerRequest.PostJobAsyncRequest;
import com.hackwiz.pragati.models.requests.PostJobRequest;
import com.hackwiz.pragati.models.responses.PostJobResponse;
import org.springframework.stereotype.Service;

@Service
public class RecruiterService {

    private final RedisHelperService redisHelperService;
    private final Producer producer;

    private final ObjectMapper objectMapper;

    public RecruiterService(RedisHelperService redisHelperService, Producer producer, ObjectMapper objectMapper) {
        this.redisHelperService = redisHelperService;
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    public PostJobResponse postJob(PostJobRequest request, String recruiterId) throws JsonProcessingException {
        if (validRequest(request)) {
            if (redisHelperService.get(getPostJobKey(request, recruiterId)) != null) {
                return PostJobResponse.builder()
                        .message("Request already in progress")
                        .success(true)
                        .build();
            } else {
                String jobId = getPostJobKey(request, recruiterId);
                redisHelperService.save(jobId, request.getJobType().name());

                producer.sendMessage(recruiterId, objectMapper.writeValueAsString(PostJobAsyncRequest.builder()
                        .jobType(request.getJobType())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .address(request.getAddress())
                        .maxRate(request.getMaxRate())
                        .recruiterId(recruiterId).build()));
                return PostJobResponse.builder()
                        .jobId(jobId)
                        .message("Job posted successfully")
                        .success(true)
                        .build();
            }
        }
        return PostJobResponse.builder()
                .message("Invalid request")
                .success(false)
                .build();
    }

    private String getPostJobKey(PostJobRequest request, String recruiterId) {
        return recruiterId + "_" + request.getJobType().name() + "_" + request.getStartDate() + "_" + request.getEndDate();
    }

    private boolean validRequest(PostJobRequest request) {
        return request != null && request.getJobType() != null;
    }


}
