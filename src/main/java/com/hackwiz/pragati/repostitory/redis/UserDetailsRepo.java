package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.UserDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepo extends CrudRepository<UserDetailsEntity, Long> {

    List<UserDetailsEntity> findByPhone(String phoneNumber);

}
