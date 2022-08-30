package com.entidadsaludusco.security.service;

import com.entidadsaludusco.models.entity.Usuario;
import com.entidadsaludusco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getUsuario(Long.valueOf(documento));
        return UsuarioPrincipalSecurity.build(usuario);
    }
}
