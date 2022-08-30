package com.entidadsaludusco.service;

import com.entidadsaludusco.models.entity.Rol;
import com.entidadsaludusco.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public void save(Rol rol){
        rolRepository.save(rol);
    }

    public Optional<Rol> getByRolNombre(String rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }
    public Rol findById(Integer id){return rolRepository.findById(id);}
    public boolean existsById(Byte idRol){
        return rolRepository.existsById(idRol);
    }
    public boolean existByRolNombre(String rolNombre){return rolRepository.existsByRolNombre(rolNombre);}
}
