package com.entidadsaludusco.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "medicamentos")
public class Medicamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    public Medicamentos(String nombre) {
        this.nombre = nombre;
    }

    public Medicamentos() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
