package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.ProfessionalDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalDetailsRepo extends CrudRepository<ProfessionalDetails, Long> {
}
