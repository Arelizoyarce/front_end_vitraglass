package com.vitroglass.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CotizacionResponseDTO {

    public Integer idCotizacion;
    public LocalDateTime fechaCotizacion;

    public String estado;

    public BigDecimal subtotal;
    public BigDecimal impuesto;
    public BigDecimal total;

    public ClienteDTO cliente;
    public UsuarioDTO usuario;

    public List<DetalleDTO> detalles;

    public static class ClienteDTO {
        public Integer idCliente;
        public String nombres;
        public String apellidos;
        public String telefono;
        public String correoElectronico;
        public String direccion;
        public String tipoCliente;
    }

    public static class UsuarioDTO {
        public Integer idUsuario;
        public String nombres;
        public String apellidos;
        public String correoElectronico;
        public String rol;
    }

    public static class DetalleDTO {
        public Integer idDetalleCotizacion;

        public Integer idTipoVidrio;
        public String nombreVidrio;

        public String descripcionProducto;

        public Double anchoMetros;
        public Double altoMetros;

        public Integer cantidad;

        public BigDecimal precioUnitario;
        public BigDecimal subtotal;
    }
}