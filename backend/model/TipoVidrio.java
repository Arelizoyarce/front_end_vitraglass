package com.vitroglass.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_vidrio")
public class TipoVidrio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_vidrio")
    private Integer idTipoVidrio;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "grosor_mm", nullable = false)
    private Double grosorMm;

    @Column(name = "precio_metro_cuadrado", nullable = false)
    private Double precioMetroCuadrado;

    @Column(nullable = false, length = 20)
    private String estado = "ACTIVO";

    public TipoVidrio() {
    }

    public TipoVidrio(Integer idTipoVidrio, String nombre, String descripcion, Double grosorMm, Double precioMetroCuadrado, String estado) {
        this.idTipoVidrio = idTipoVidrio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.grosorMm = grosorMm;
        this.precioMetroCuadrado = precioMetroCuadrado;
        this.estado = estado != null ? estado : "ACTIVO";
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
