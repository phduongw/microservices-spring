package com.dcorp.hightech.api.users.photoappapiusers.repositories;

import com.dcorp.hightech.api.users.photoappapiusers.entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
