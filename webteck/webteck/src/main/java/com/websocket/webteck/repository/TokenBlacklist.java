package com.websocket.webteck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.websocket.webteck.model.Blacklist;

public interface TokenBlacklist extends JpaRepository<Blacklist, String>{
    boolean existsByToken(String token);
}
