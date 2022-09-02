package com.entidadsaludusco.service;

import com.entidadsaludusco.models.entity.Rol;
import com.entidadsaludusco.models.entity.Usuario;
import com.entidadsaludusco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> findUsuariosByRolId(Long id){return usuarioRepository.findUsuariosByRolId(id);}
    public Usuario getUsuario(Long documento){
        return usuarioRepository.findByDocumento(documento);
    }
    public boolean existByDocumento(Long documento){
        return usuarioRepository.existsByDocumento(documento);
    }
    public void save(Usuario usuario){usuarioRepository.save(usuario);}

}
