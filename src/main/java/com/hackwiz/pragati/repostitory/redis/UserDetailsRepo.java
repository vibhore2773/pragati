package com.hackwiz.pragati.repostitory.redis;

import com.hackwiz.pragati.dao.redis.UserDetailsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDetailsRepo extends CrudRepository<UserDetailsEntity, String> {

    List<UserDetailsEntity> findByPhoneNumber(String phoneNumber);

}
