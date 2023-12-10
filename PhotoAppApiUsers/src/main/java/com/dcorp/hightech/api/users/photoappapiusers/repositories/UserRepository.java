package com.dcorp.hightech.api.users.photoappapiusers.repositories;

import com.dcorp.hightech.api.users.photoappapiusers.entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

}
