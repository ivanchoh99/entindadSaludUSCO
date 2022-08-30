package com.entidadsaludusco.models.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "rol") public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;
    @Column(unique = true)
    private String rolNombre;

    public Rol() {
    }

    public Rol( String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    @NonNull
    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }
}
