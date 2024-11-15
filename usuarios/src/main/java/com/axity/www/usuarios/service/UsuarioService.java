package com.axity.www.usuarios.service;

import com.axity.www.usuarios.UsuariosApplication;
import com.axity.www.usuarios.models.entity.Usuario;

import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();

    Optional<Usuario> porId(Long id);

    Usuario guardar(Usuario usuario);

    void eliminar(Long id);

    Optional<Usuario> porEmail(String email);

    List<Usuario> listarPorId(Iterable<Long> ids);




}
