package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.ProfessionalDetailJobDetailMapEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalDetailJobDetailMapRepo extends CrudRepository<ProfessionalDetailJobDetailMapEntity, String> {
    List<ProfessionalDetailJobDetailMapEntity> findByProfessionalDetailId(String professionalDetailId);
    List<ProfessionalDetailJobDetailMapEntity> findByJobDetailId(String jobDetailId);
}
