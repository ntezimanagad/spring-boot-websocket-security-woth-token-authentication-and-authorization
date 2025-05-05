package com.websocket.webteck.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Blacklist {
    @Id
    private String token;
    private Instant blacklistAt;
    public Blacklist() {
    }
    public Blacklist(String token, Instant blacklistAt) {
        this.token = token;
        this.blacklistAt = blacklistAt;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Instant getBlacklistAt() {
        return blacklistAt;
    }
    public void setBlacklistAt(Instant blacklistAt) {
        this.blacklistAt = blacklistAt;
    }
    
}
