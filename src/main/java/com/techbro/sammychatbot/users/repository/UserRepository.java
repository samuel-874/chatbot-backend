package com.techbro.sammychatbot.users.repository;

import com.techbro.sammychatbot.users.model.Roles;
import com.techbro.sammychatbot.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmailAndRoles(String email, Roles admin);
}
