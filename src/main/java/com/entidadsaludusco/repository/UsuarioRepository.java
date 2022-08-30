package com.entidadsaludusco.repository;

import com.entidadsaludusco.models.entity.Rol;
import com.entidadsaludusco.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {

    @Query(value = "SELECT U.*,R.id FROM usuario U INNER JOIN usuario_rol UR ON UR.usuario_id = U.documento INNER JOIN rol R ON R.id = UR.rol_id WHERE R.id =:id",nativeQuery = true)
    List<Usuario> findUsuariosByRolId(@Param("id") Integer id);
    List<Usuario> findAll();
    Usuario findByDocumento(Integer documento);
    boolean existsByDocumento(Integer documento);


}
