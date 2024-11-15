package com.axity.www.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cursos", url = "localhost:8112")
public interface CursoClientRest {

    @DeleteMapping("/eliminar-usuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);

}
