package com.entidadsaludusco.service;

import com.entidadsaludusco.models.entity.Consultorio;
import com.entidadsaludusco.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {

    @Autowired
    ConsultorioRepository consultorioRepository;

    public List<Consultorio> getConsultorios(){
        return consultorioRepository.findAll();
    }
    public Consultorio getById(Long id){
        return consultorioRepository.getById(id);
    }
    public void save(Consultorio consultorio){ consultorioRepository.save(consultorio);}
}
