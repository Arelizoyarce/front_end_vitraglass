package com.vitroglass.backend.dto;

public class DetalleRequestDTO {

    private Integer idTipoVidrio;
    private Integer cantidad;
    private Double ancho;
    private Double alto;
    private Double precioUnitario;

    public DetalleRequestDTO() {
    }

    public DetalleRequestDTO(Integer idTipoVidrio, Integer cantidad, Double ancho, Double alto, Double precioUnitario) {
        this.idTipoVidrio = idTipoVidrio;
        this.cantidad = cantidad;
        this.ancho = ancho;
        this.alto = alto;
        this.precioUnitario = precioUnitario;
    }

    public Integer getIdTipoVidrio() {
        return idTipoVidrio;
    }

    public void setIdTipoVidrio(Integer idTipoVidrio) {
        this.idTipoVidrio = idTipoVidrio;
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

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
