/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vitroglass.backend.dto;

/**
 *
 * @author Areliz
 */
public class DetalleRequestDTO {

    private Integer idTipoVidrio;
    private String descripcionProducto;
    private Double anchoMetros;
    private Double altoMetros;
    private Integer cantidad;
    private Double precioUnitario;

    public Integer getIdTipoVidrio() {
        return idTipoVidrio;
    }

    public void setIdTipoVidrio(Integer idTipoVidrio) {
        this.idTipoVidrio = idTipoVidrio;
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
}
