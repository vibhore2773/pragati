package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.ProfessionalDetails;
import com.hackwiz.pragati.dao.redis.UserDetailsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfessionalDetailsRepo extends CrudRepository<ProfessionalDetails, Long> {

    List<ProfessionalDetails> findByUserId(Long userId);

}
