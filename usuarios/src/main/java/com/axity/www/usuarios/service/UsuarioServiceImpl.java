package com.axity.www.usuarios.service;

import com.axity.www.usuarios.client.CursoClientRest;
import com.axity.www.usuarios.models.entity.Usuario;
import com.axity.www.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CursoClientRest client;


    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public void eliminar(Long id) {
            repository.deleteById(id);
            client.eliminarCursoUsuarioPorId(id);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<Usuario> listarPorId(Iterable<Long> ids) {
        return (List<Usuario>) repository.findAllById(ids);
    }
}
