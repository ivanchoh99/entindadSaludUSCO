package com.entidadsaludusco.repository;

import com.entidadsaludusco.models.entity.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultorioRepository extends JpaRepository<Consultorio,Long> {

    List<Consultorio> findAll();
}
