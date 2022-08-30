package com.entidadsaludusco.security.service;

import com.entidadsaludusco.models.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioPrincipalSecurity implements UserDetails {


    private Long documento;//Funciona como el username
    private String password;

    private String nombre;
    private String apellido;
    private String sireccion;
    private Long celular;
    private Collection<? extends GrantedAuthority> authoritie;

    public UsuarioPrincipalSecurity(Long documento, String password, String nombre, String apellido, String sireccion, Long celular, Collection<?extends GrantedAuthority> authoritie) {
        this.documento = documento;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sireccion = sireccion;
        this.celular = celular;
        this.authoritie = authoritie;
    }

    public static UsuarioPrincipalSecurity build(Usuario usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre())).collect(Collectors.toList());

        return new UsuarioPrincipalSecurity(usuario.getDocumento()
                                            ,usuario.getPassword()
                                            ,usuario.getNombre()
                                            , usuario.getApellido()
                                            , usuario.getDireccion()
                                            , usuario.getCelular()
                                            ,authorities);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {return authoritie;}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(documento);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
