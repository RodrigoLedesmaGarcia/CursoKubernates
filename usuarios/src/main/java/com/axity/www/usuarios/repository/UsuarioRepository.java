package com.axity.www.usuarios.repository;

import com.axity.www.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository <Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}
