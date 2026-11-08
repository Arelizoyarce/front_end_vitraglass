package com.vitroglass.backend.service;

import com.vitroglass.backend.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioService usuarioService;

    public LoginResponse login(String correo, String contrasena) {
        return usuarioService.login(correo, contrasena);
    }
}