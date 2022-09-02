package com.entidadsaludusco.models.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Consultorio consultorio;

    private LocalDateTime fechaHora;

    @OneToOne
    private Usuario medico;
    @OneToOne
    private Usuario paciente;

    private String diagnostico;
    private String recomendaciones;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name =  "cita_medicamentos",
    joinColumns=@JoinColumn(name="cita_id"),
    inverseJoinColumns=@JoinColumn(name="medicamento_id"))
    private List<Medicamentos> medicamentos;

    public Cita(Consultorio consultorio, Usuario medico, Usuario paciente, LocalDateTime fechaHora, String diagnostico) {
        this.consultorio = consultorio;
        this.medico = medico;
        this.paciente = paciente;
        this.fechaHora = fechaHora;
        this.diagnostico = diagnostico;
    }

    public Cita() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario paciente) {
        this.paciente = paciente;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public List<Medicamentos> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamentos> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
}
