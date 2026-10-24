package com.vitroglass.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_cotizacion")
public class DetalleCotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cotizacion", nullable = false)
    @JsonIgnore
    private Cotizacion cotizacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_vidrio", nullable = false)
    private TipoVidrio tipoVidrio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double ancho;

    @Column(nullable = false)
    private Double alto;

    @Column(nullable = false)
    private Double area;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private Double subtotal;

    public DetalleCotizacion() {
    }

    public DetalleCotizacion(Integer idDetalle, Cotizacion cotizacion, TipoVidrio tipoVidrio, Integer cantidad, Double ancho, Double alto, Double area, Double precioUnitario, Double subtotal) {
        this.idDetalle = idDetalle;
        this.cotizacion = cotizacion;
        this.tipoVidrio = tipoVidrio;
        this.cantidad = cantidad;
        this.ancho = ancho;
        this.alto = alto;
        this.area = area;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public TipoVidrio getTipoVidrio() {
        return tipoVidrio;
    }

    public void setTipoVidrio(TipoVidrio tipoVidrio) {
        this.tipoVidrio = tipoVidrio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getAlto() {
        return alto;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
