package com.axity.www.cursos.services;

import com.axity.www.cursos.models.Usuario;
import com.axity.www.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Curso guardar(Curso curso);

    void eliminar (Long id);

    Optional<Curso> porIdConUusario(Long id);

    void eliminarCursousuarioPorId(Long id);


    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);

}
