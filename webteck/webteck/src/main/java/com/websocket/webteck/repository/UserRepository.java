package com.websocket.webteck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.websocket.webteck.model.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
