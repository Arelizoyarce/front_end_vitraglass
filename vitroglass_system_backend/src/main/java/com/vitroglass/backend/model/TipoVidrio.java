package com.vitroglass.backend.model;

import jakarta.persistence.*;
@Entity
@Table(name = "tipo_vidrio")
public class TipoVidrio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_vidrio")
    private Integer idTipoVidrio;

    private String nombre;

    private String descripcion;

    @Column(name = "grosor_mm")
    private Double grosorMm;

    @Column(name = "precio_metro_cuadrado")
    private Double precioMetroCuadrado;

    private String estado;

    public TipoVidrio() {
    }

    public Integer getIdTipoVidrio() {
        return idTipoVidrio;
    }

    public void setIdTipoVidrio(Integer idTipoVidrio) {
        this.idTipoVidrio = idTipoVidrio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getGrosorMm() {
        return grosorMm;
    }

    public void setGrosorMm(Double grosorMm) {
        this.grosorMm = grosorMm;
    }

    public Double getPrecioMetroCuadrado() {
        return precioMetroCuadrado;
    }

    public void setPrecioMetroCuadrado(Double precioMetroCuadrado) {
        this.precioMetroCuadrado = precioMetroCuadrado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}