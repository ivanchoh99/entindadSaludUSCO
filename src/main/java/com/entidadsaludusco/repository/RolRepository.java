package com.entidadsaludusco.repository;

import com.entidadsaludusco.models.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Byte> {
    @Query(value = "SELECT * from rol where rol.id=:id",nativeQuery = true)
    Rol findById(@Param("id") Integer id);
    boolean existsById(Byte id);
    Optional<Rol> findByRolNombre(String rolNombre);
    boolean existsByRolNombre(String rolNombre);

    }
