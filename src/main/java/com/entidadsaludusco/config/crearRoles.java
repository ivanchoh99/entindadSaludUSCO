package com.entidadsaludusco.config;

import com.entidadsaludusco.models.entity.Rol;
import com.entidadsaludusco.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class crearRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
//        Rol rolAdmin = new Rol("ADMIN");
//        Rol rolMedico = new Rol("MEDICO");
//        Rol rolPaciente = new Rol("PACIENTE");
//        rolService.save(rolAdmin);
//        rolService.save(rolMedico);
//        rolService.save(rolPaciente);
    }
}
