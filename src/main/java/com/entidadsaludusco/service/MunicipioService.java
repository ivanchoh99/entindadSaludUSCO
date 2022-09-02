package com.entidadsaludusco.service;

import com.entidadsaludusco.models.entity.Municipio;
import com.entidadsaludusco.repository.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MunicipioService {

    @Autowired
    MunicipioRepository municipioRepository;

    public List<Municipio> lista(){
        return municipioRepository.findAll();
    }
    public Municipio getMunicipio(Long id){return municipioRepository.getByIdIs(id);}



}
