package com.axity.www.cursos.services;

import com.axity.www.cursos.client.UsuarioClientRest;
import com.axity.www.cursos.models.Usuario;
import com.axity.www.cursos.models.entity.Curso;
import com.axity.www.cursos.models.entity.CursoUsuario;
import com.axity.www.cursos.repository.CursoRepository;
import jakarta.transaction.Transactional;
import org.hibernate.resource.beans.container.spi.BeanContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest client;


    //  CRUD DE CURSOS ===============================//
    @Override
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    public void eliminar(Long id) {
           repository.deleteById(id);
    }

    @Override
    public Optional<Curso> porIdConUusario(Long id) {
        Optional<Curso> optional = repository.findById(id);
        if(optional.isPresent()){
            Curso curso = optional.get();

            if(!curso.getCursoUsuarios().isEmpty()) {
                List<Long> ids = curso.getCursoUsuarios().stream().map( cu -> cu.getUsuarioId()).
                        collect(Collectors.toList());

                List<Usuario> usuarios = client.obteberAlumnosCurso(ids);
                curso.setUsuarios(usuarios);
            }

            return Optional.of(curso);
        }
        return Optional.empty();
    }


    @Override
    public void eliminarCursousuarioPorId(Long id) {
        repository.eliminarCursoUsuarioPorId(id);
    }

    //------------------------------------------------------------


    // CRUD DE CURSO-USUARIO =====================================
    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> optional = repository.findById(cursoId);
        if(optional.isPresent()){
            Usuario usuarioMs = client.detalle(usuario.getId());
            Curso curso = optional.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMs.getId());
            curso.addCursoUsuarios(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMs);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> optional = repository.findById(cursoId);
        if(optional.isPresent()){

            Usuario usuarioNuevoMs = client.crear(usuario);
            Curso curso = optional.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMs.getId());
            curso.addCursoUsuarios(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioNuevoMs);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }
    //----------------------------------------------------------------------------
}
