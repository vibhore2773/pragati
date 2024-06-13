package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.ProfessionalDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalDetailsRepo extends CrudRepository<ProfessionalDetails, String> {
    List<ProfessionalDetails> findByUserId(String userId);
}
