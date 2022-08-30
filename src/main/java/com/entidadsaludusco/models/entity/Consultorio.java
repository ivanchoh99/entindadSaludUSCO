package com.entidadsaludusco.models.entity;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "consultorio")
public class Consultorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Municipio municipio;

    public Consultorio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Consultorio() {
    }

    public Long getId() {
        return id;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
}
