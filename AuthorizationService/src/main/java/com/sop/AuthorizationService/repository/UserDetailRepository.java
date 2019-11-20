package com.sop.AuthorizationService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sop.AuthorizationService.model.User;

public interface UserDetailRepository extends JpaRepository<User,Integer> {


    Optional<User> findByUsername(String name);

}
