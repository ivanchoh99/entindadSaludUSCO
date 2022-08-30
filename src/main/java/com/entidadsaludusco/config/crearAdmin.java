package com.entidadsaludusco.config;

import com.entidadsaludusco.models.entity.Rol;
import com.entidadsaludusco.models.entity.Usuario;
import com.entidadsaludusco.service.RolService;
import com.entidadsaludusco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class  crearAdmin implements CommandLineRunner {

        @Autowired
        UsuarioService usuarioService;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        RolService rolService;

        @Override
        public void run(String... args) throws Exception {
//        Usuario usuario = new Usuario();
//        String passwordEncoded = passwordEncoder.encode("admin");
//        usuario.setDocumento(1075321508);
//        usuario.setPassword(passwordEncoded);
//        usuario.setNombre("ADMIN");
//        usuario.setApellido("ADMIN");
//        usuario.setDireccion("ADMIN");
//        usuario.setCelular(1000000000L);
//        Rol rol = rolService.findById(1);
//        Set<Rol> roles = new HashSet<>();
//        roles.add(rol);
//        usuario.setRoles(roles);
//        usuarioService.save(usuario);
        }
    }

