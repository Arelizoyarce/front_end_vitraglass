package com.vitroglass.backend.dto;

public class LoginResponse {

    private final Integer idUsuario;
    private final String nombres;
    private final String apellidos;
    private final String correoElectronico;
    private final String rol;
    private final String mensaje;
    private final String token;

    public LoginResponse(Integer idUsuario, String nombres,
                         String apellidos, String correoElectronico,
                         String rol, String mensaje, String token) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.rol = rol;
        this.mensaje = mensaje;
        this.token = token;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreoElectronico() { return correoElectronico; }
    public String getRol() { return rol; }
    public String getMensaje() { return mensaje; }
    public String getToken() { return token; }
}