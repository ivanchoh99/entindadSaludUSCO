package com.entidadsaludusco.service;

import com.entidadsaludusco.models.entity.Cita;
import com.entidadsaludusco.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitasService {

    @Autowired
    CitaRepository citaRepository;

    public List<Cita> findAll(){
        return citaRepository.findAll();
    }

    public void save(Cita cita){citaRepository.save(cita);}

    public void delet(Long id){citaRepository.deleteById(id);}
}
