package com.vitroglass.backend.dto;

import java.util.List;

public class CotizacionRequestDTO {

    private String cliente;
    private String fechaEntrega;
    private List<DetalleRequestDTO> detalles;

    public CotizacionRequestDTO() {
    }

    public CotizacionRequestDTO(String cliente, String fechaEntrega, List<DetalleRequestDTO> detalles) {
        this.cliente = cliente;
        this.fechaEntrega = fechaEntrega;
        this.detalles = detalles;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public List<DetalleRequestDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleRequestDTO> detalles) {
        this.detalles = detalles;
    }
}
