package com.entidadsaludusco.repository;

import com.entidadsaludusco.models.entity.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MunicipioRepository extends JpaRepository<Municipio,Long> {

    List<Municipio> findAll();
    Municipio findByIdIs( Long id);



}
