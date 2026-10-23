package com.vitroglass.backend.controller;

import com.vitroglass.backend.dto.LoginRequest;
import com.vitroglass.backend.dto.LoginResponse;
import com.vitroglass.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(
                request.getCorreoElectronico(),
                request.getContrasena()
        );
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/hash")
public String generarHash() {
    return passwordEncoder.encode("123456");
}
}