package com.axity.www.cursos.controller;

import com.axity.www.cursos.models.Usuario;
import com.axity.www.cursos.models.entity.Curso;
import com.axity.www.cursos.services.CursoService;
import feign.FeignException;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController {


    @Autowired
    private CursoService service;


    // Metodo para listar la lista de cursos =====================
    // http://localhost:8112/
    @GetMapping("/")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }// fin del metodo listar
   //--------------------------


    // Metodo para ver el detalle de los cursos ====================
    // http://localhost:8112/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> detalles(@PathVariable Long id) {
        Optional<Curso> optional = service.porIdConUusario(id);
        if(optional.isPresent()){
            return ResponseEntity.ok(optional.get());
        }
        return ResponseEntity.notFound().build();
    }// fin del metodo para ver el detalle de los cursos
   //-----------------------------------------------------


   // Metodo para crear cursos nuevos =========================
   // http://localhost:8112/
    @PostMapping("/")
    public ResponseEntity<?> crear (@Valid @RequestBody Curso curso, BindingResult result) {

        if (result.hasErrors()) {
            return getValidar(result);
        }// metodo if para validar

        Curso cursodb = service.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursodb);

    }// fin del metodo para crear cursos
   //-------------------------------------


    //Metodo para editar cursos ======================
    // http://localhost:8112/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> editar (@Valid @RequestBody Curso curso, BindingResult result ,@PathVariable Long id) {

        if (result.hasErrors()) {
            return getValidar(result);
        } //metodo if para validar

        Optional<Curso> optional = service.porId(id);
        if(optional.isPresent()){
            Curso cursodb = optional.get();
            cursodb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curso));
        }
        return ResponseEntity.notFound().build();
    }// fin del metodo para crear curso
   //--------------------------------------


    // Metodo para eliminar un curso ===================
    //http://localhost:8112/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> optional= service.porId(id);
        if(optional.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }// fin del metodo para eliminar
   //---------------------------------


    // Metodo asignar usuario al curso =========================
    // http://localhost:8112/asignar-usuario/{cursoId}
    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
       Optional<Usuario> optional;
       try {
           optional = service.asignarUsuario(usuario, cursoId);
       }catch (FeignException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "Un error a ocurrido..."));
       }

       if(optional.isPresent()) {
           return ResponseEntity.status(HttpStatus.CREATED).body(optional.get());
       }

       return ResponseEntity.notFound().build();

    }// fin del metodo asignar usuario curso
   //----------------------------------


    // Metodo crear usuario al curso ============================
    // http://localhost:8112/crear-usuario/{cursoId}
    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> optional;
        try {
            optional = service.crearUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "Un error a ocurrido..."));
        }

        if (optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optional.get());
        }

        return ResponseEntity.notFound().build();

    }// fin del metodo crear usuario curso
   //-----------------------------------------

    // Metodo borrar usuario al curso ============================
    // http://localhost:8112/eliminar-usuario/{cursoId}
    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> optional;
        try {
            optional = service.eliminarUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el id o error en la comunicacion: " + e.getMessage()));
        }
        if (optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optional.get());
        }
        return ResponseEntity.notFound().build();
    }// fin del metodo eliminar usuario curso
    //-----------------------------------------



    // Metodo para eliminar

    @DeleteMapping("/eliminar-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        service.eliminarCursousuarioPorId(id);

        return ResponseEntity.noContent().build();
    }// fin del metodo
   //-------------------------



    //Metdo para validar =======================================================
    private static ResponseEntity<Map<String, String>> getValidar(BindingResult result) {
        Map<String, String> errores = result.getFieldErrors().stream().collect(Collectors.toMap(err -> err.getField(), err -> "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }// fin del metodo para validar
    //--------------------------------

}// fin de cursoController
