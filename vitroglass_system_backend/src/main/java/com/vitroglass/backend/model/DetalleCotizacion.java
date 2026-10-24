package com.vitroglass.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_cotizacion")
public class DetalleCotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_cotizacion")
    private Integer idDetalleCotizacion;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_cotizacion")
    private Cotizacion cotizacion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_vidrio")
    private TipoVidrio tipoVidrio;

    @Column(name = "descripcion_producto")
    private String descripcionProducto;

    @Column(name = "ancho_metros")
    private Double anchoMetros;

    @Column(name = "alto_metros")
    private Double altoMetros;

    @Column(name = "area_metros_cuadrados")
    private Double areaMetrosCuadrados;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    private Double subtotal;

    public DetalleCotizacion() {
    }

    public Integer getIdDetalleCotizacion() {
        return idDetalleCotizacion;
    }

    public void setIdDetalleCotizacion(Integer idDetalleCotizacion) {
        this.idDetalleCotizacion = idDetalleCotizacion;
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

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Double getAnchoMetros() {
        return anchoMetros;
    }

    public void setAnchoMetros(Double anchoMetros) {
        this.anchoMetros = anchoMetros;
    }

    public Double getAltoMetros() {
        return altoMetros;
    }

    public void setAltoMetros(Double altoMetros) {
        this.altoMetros = altoMetros;
    }

    public Double getAreaMetrosCuadrados() {
        return areaMetrosCuadrados;
    }

    public void setAreaMetrosCuadrados(Double areaMetrosCuadrados) {
        this.areaMetrosCuadrados = areaMetrosCuadrados;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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