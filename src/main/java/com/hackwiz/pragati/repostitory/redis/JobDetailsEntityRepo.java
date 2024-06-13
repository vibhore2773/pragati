package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDetailsEntityRepo extends CrudRepository<JobDetailsEntity, String> {
}
