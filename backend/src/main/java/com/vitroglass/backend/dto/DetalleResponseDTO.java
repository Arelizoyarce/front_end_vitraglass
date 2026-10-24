package com.vitroglass.backend.dto;

public class DetalleResponseDTO {

    private Integer idDetalle;
    private Integer idTipoVidrio;
    private String nombreTipoVidrio;
    private Double grosorMm;
    private Integer cantidad;
    private Double ancho;
    private Double alto;
    private Double area;
    private Double precioUnitario;
    private Double subtotal;

    public DetalleResponseDTO() {
    }

    public DetalleResponseDTO(Integer idDetalle, Integer idTipoVidrio, String nombreTipoVidrio, Double grosorMm, Integer cantidad, Double ancho, Double alto, Double area, Double precioUnitario, Double subtotal) {
        this.idDetalle = idDetalle;
        this.idTipoVidrio = idTipoVidrio;
        this.nombreTipoVidrio = nombreTipoVidrio;
        this.grosorMm = grosorMm;
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

    public Integer getIdTipoVidrio() {
        return idTipoVidrio;
    }

    public void setIdTipoVidrio(Integer idTipoVidrio) {
        this.idTipoVidrio = idTipoVidrio;
    }

    public String getNombreTipoVidrio() {
        return nombreTipoVidrio;
    }

    public void setNombreTipoVidrio(String nombreTipoVidrio) {
        this.nombreTipoVidrio = nombreTipoVidrio;
    }

    public Double getGrosorMm() {
        return grosorMm;
    }

    public void setGrosorMm(Double grosorMm) {
        this.grosorMm = grosorMm;
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
