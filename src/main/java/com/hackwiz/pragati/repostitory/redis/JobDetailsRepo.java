package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobDetailsRepo extends CrudRepository<JobDetailsEntity, Long> {

    List<JobDetailsEntity> findAllByRecruiterDetailsId(Long recruiterDetailsId);
}
