package com.vitroglass.backend.config;

import com.vitroglass.backend.model.Usuario;
import com.vitroglass.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDetailsService
        implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        Usuario usuario =
                usuarioRepository
                        .findByCorreoElectronico(correo)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Usuario no encontrado"));

        return new User(
                usuario.getCorreoElectronico(),
                usuario.getContrasena(),
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + usuario.getRol()
                        )
                )
        );
    }
}