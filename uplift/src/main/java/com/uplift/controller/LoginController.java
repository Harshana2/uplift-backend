package com.uplift.controller;

import com.uplift.dto.LoginRequest;
import com.uplift.dto.UserDTO;
import com.uplift.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserDTO userDTO = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
