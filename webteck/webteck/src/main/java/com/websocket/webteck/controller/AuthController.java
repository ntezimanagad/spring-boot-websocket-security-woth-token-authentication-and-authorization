package com.websocket.webteck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websocket.webteck.dto.UserDTO;
import com.websocket.webteck.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private UserService userService;
    
    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return ResponseEntity.ok("User Created");
    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.loginUser(userDTO));
    }
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Beaer ")) {
            token = token.substring(7);
            userService.logout(token);
        }
        return ResponseEntity.ok("Successfull Loged Out");
    }
}
