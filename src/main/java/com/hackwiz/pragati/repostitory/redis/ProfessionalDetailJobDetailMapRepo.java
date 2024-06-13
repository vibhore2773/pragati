package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.ProfessionalDetailJobDetailMapEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalDetailJobDetailMapRepo extends CrudRepository<ProfessionalDetailJobDetailMapEntity, Long> {
}
