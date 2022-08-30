package com.entidadsaludusco.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "departamento")
public class Departamento {
    @Id
    private Long id;
    private String nombre;
}
