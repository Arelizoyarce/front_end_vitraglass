package com.vitroglass.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CotizacionResponseDTO {

    private Integer id;
    private Integer idCotizacion;
    private String cliente;
    private LocalDateTime fecha;
    private String fechaEntrega;
    private Integer items;
    private Double total;
    private String estado;
    private List<DetalleResponseDTO> detalles;

    public CotizacionResponseDTO() {
    }

    public CotizacionResponseDTO(Integer idCotizacion, String cliente, LocalDateTime fecha, String fechaEntrega, Integer items, Double total, String estado, List<DetalleResponseDTO> detalles) {
        this.id = idCotizacion;
        this.idCotizacion = idCotizacion;
        this.cliente = cliente;
        this.fecha = fecha;
        this.fechaEntrega = fechaEntrega;
        this.items = items;
        this.total = total;
        this.estado = estado;
        this.detalles = detalles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.idCotizacion = id;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
        this.id = idCotizacion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleResponseDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleResponseDTO> detalles) {
        this.detalles = detalles;
        if (detalles != null) {
            this.items = detalles.size();
        }
    }
}
