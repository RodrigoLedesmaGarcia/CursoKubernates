package com.axity.www.cursos.client;

import com.axity.www.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="usuarios", url="usuarios:8111")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    public Usuario detalle(@PathVariable Long id);


    @PostMapping("/")
    public Usuario crear (@RequestBody Usuario usuario);


    @GetMapping("/usuarios-curso")
    List<Usuario> obteberAlumnosCurso(@RequestParam Iterable<Long> ids);



}
