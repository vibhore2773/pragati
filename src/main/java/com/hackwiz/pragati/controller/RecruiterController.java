package com.hackwiz.pragati.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.ExceptionUtil;
import com.hackwiz.pragati.models.requests.PostJobRequest;
import com.hackwiz.pragati.models.responses.PostJobResponse;
import com.hackwiz.pragati.service.RecruiterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/recruiter")
@Slf4j
public class RecruiterController {

    private final RecruiterService recruiterService;

    public RecruiterController(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @PostMapping("/post-job")
    public ResponseEntity postJob(@RequestBody PostJobRequest request, @RequestHeader("recruiter_id") String recruiterId) {
        PostJobResponse response = null;
        try {
            log.info("Posting job for recruiter: {}, request : {}", recruiterId, request.toString());
            response = recruiterService.postJob(request, recruiterId);
            log.info("Job posted successfully for recruiter: {}, response : {}", recruiterId, response.toString());
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException while posting job", e.getStackTrace());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("Exception while posting job", e.getStackTrace());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(response);
    }
}
