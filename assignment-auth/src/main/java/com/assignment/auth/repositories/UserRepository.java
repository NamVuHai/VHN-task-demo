package com.assignment.auth.repositories;

import com.assignment.auth.entities.UserEntity;
import com.assignment.core.common.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CommonRepository<UserEntity, String> {
    Optional<UserEntity> findByUserName(String username);
}
