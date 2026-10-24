package com.vitroglass.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Integer idCotizacion;

    @Column(nullable = false, length = 150)
    private String cliente;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(name = "fecha_entrega")
    private String fechaEntrega;

    @Column(nullable = false, length = 30)
    private String estado = "COTIZADO";

    @Column(nullable = false)
    private Double total = 0.0;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleCotizacion> detalles = new ArrayList<>();

    public Cotizacion() {
        this.fecha = LocalDateTime.now();
        this.estado = "COTIZADO";
    }

    public Cotizacion(Integer idCotizacion, String cliente, LocalDateTime fecha, String fechaEntrega, String estado, Double total) {
        this.idCotizacion = idCotizacion;
        this.cliente = cliente;
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
        this.fechaEntrega = fechaEntrega;
        this.estado = estado != null ? estado : "COTIZADO";
        this.total = total != null ? total : 0.0;
    }

    public void addDetalle(DetalleCotizacion detalle) {
        detalles.add(detalle);
        detalle.setCotizacion(this);
    }

    public void removeDetalle(DetalleCotizacion detalle) {
        detalles.remove(detalle);
        detalle.setCotizacion(null);
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DetalleCotizacion> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCotizacion> detalles) {
        this.detalles = detalles;
    }
}
