package com.vitroglass.backend.service;

import com.vitroglass.backend.config.JwtUtil;
import com.vitroglass.backend.dto.LoginResponse;
import com.vitroglass.backend.model.Usuario;
import com.vitroglass.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioService {
    
        @Autowired
private JwtUtil jwtUtil;
        
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {

    usuario.setContrasena(
            passwordEncoder.encode(
                    usuario.getContrasena()
            )
    );

    return usuarioRepository.save(usuario);
}

    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }
    

public LoginResponse login(String correo, String contrasena) {
    Optional<Usuario> usuarioOptional =
            usuarioRepository.findByCorreoElectronico(correo);

    if (usuarioOptional.isEmpty()) {
        throw new RuntimeException("Usuario no encontrado");
    }

    Usuario usuario = usuarioOptional.get();

    if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
        throw new RuntimeException("Contraseña incorrecta");
    }

    String token = jwtUtil.generateToken(
            usuario.getCorreoElectronico(),
            usuario.getRol()
    );

    return new LoginResponse(
            usuario.getIdUsuario(),
            usuario.getNombres(),
            usuario.getApellidos(),
            usuario.getCorreoElectronico(),
            usuario.getRol(),
            "Login exitoso",
            token
    );
}

public Usuario actualizar(Integer id, Usuario datos) {
    Usuario existing = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    existing.setNombres(datos.getNombres());
    existing.setApellidos(datos.getApellidos());
    existing.setCorreoElectronico(datos.getCorreoElectronico());
    existing.setRol(datos.getRol());
    existing.setEstado(datos.getEstado());
    // Solo actualiza contraseña si se envía una nueva
    if (datos.getContrasena() != null && !datos.getContrasena().isEmpty()) {
        existing.setContrasena(passwordEncoder.encode(datos.getContrasena()));
    }
    return usuarioRepository.save(existing);
}

public void desactivar(Integer id) {
    Usuario existing = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    existing.setEstado("INACTIVO");
    usuarioRepository.save(existing);
}

}