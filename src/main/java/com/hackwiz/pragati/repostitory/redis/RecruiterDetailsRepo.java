package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.RecruiterDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterDetailsRepo extends CrudRepository<RecruiterDetailsEntity, Long> {
    RecruiterDetailsEntity findByUserId(Long userId);
}
