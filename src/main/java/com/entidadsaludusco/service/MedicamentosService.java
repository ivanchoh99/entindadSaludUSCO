package com.entidadsaludusco.service;

import com.entidadsaludusco.models.entity.Medicamentos;
import com.entidadsaludusco.repository.MedicamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicamentosService {

    @Autowired
    MedicamentosRepository medicamentosRepository;

    public List<Medicamentos> findAll(){
     return medicamentosRepository.findAll();
    }
    public boolean existId(Long id){ return medicamentosRepository.existsById(id);}

    public Medicamentos getById(Long id){ return  medicamentosRepository.getById(id);}
}
