package com.entidadsaludusco.repository;

import com.entidadsaludusco.models.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita,Long> {

    @Query(value = "select * from cita where cita.paciente_documento=:id",nativeQuery = true)
    List<Cita> getCitasByPacienteId(@Param("id") Long id);

    @Query(value = "select * from cita where cita.medico_documento=:id",nativeQuery = true)
    List<Cita> getCitasByMedicoId(@Param("id") Long id);

    List<Cita> findAll();

    Cita getById(Long id);


//    List<Cita> getCitaByFechaHoraMonth(int i);

}
