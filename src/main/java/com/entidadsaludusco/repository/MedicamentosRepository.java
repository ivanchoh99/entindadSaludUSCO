package com.entidadsaludusco.repository;

import com.entidadsaludusco.models.entity.Medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentosRepository  extends JpaRepository<Medicamentos, Long> {

    @Query(value = "SELECT * FROM medicamentos",nativeQuery = true)
    List<Medicamentos> findAll();
    boolean existsById(Long id);
    Medicamentos getById(Long id);
}
