package com.entidadsaludusco.service;

import com.entidadsaludusco.models.entity.Cita;
import com.entidadsaludusco.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitasService {

    CitaRepository citaRepository;

    public List<Cita> findAll(){
        return citaRepository.findAll();
    }
}
