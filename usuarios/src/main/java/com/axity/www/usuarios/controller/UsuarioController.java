package com.axity.www.usuarios.controller;

import com.axity.www.usuarios.models.entity.Usuario;
import com.axity.www.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    //Metodo para listar una lista de los usuarios ========================
    // http://localhost:8111/
    @GetMapping("/")
    public List<Usuario> listar() {
        return service.listar();
    }// fin del metodo listar
   //-----------------------------


    //Metodo para ver el detalle de usuarios ==============================
    //http://localhost:8111/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id) {
        Optional<Usuario> optional = service.porId(id);
        if(optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }
        return ResponseEntity.notFound().build();
    } // fin del metodo que muestra el detalle
   //-----------------------------------------


    //Metodo para ver crear usuarios ====================================
    // http://localhost:8111/{id}
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {

        if(!usuario.getEmail().isEmpty() && service.porEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "ese correo ya esta registrado"));
        }

        if (result.hasErrors()) {
            return getValidar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }// fin del metodo que crea usuarios nuevos
    //-----------------------------------------


    //Metodo para ver editar usuarios ====================================
    // http://localhost:8111/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> editar (@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){

        if (result.hasErrors()) {
            return getValidar(result);
        }
        Optional <Usuario> optional= service.porId(id);
            if(optional.isPresent()){
                Usuario usuarioDb = optional.get();

                if(!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) && service.porEmail(usuario.getEmail()).isPresent()){
                    return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "ese correo ya esta registrado"));
                }

                usuarioDb.setNombre(usuario.getNombre());
                usuarioDb.setEmail(usuario.getEmail());
                usuarioDb.setPassword(usuario.getPassword());
                return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
            }
            return ResponseEntity.notFound().build();
    } // fin del metodo de editar usuarios
   //----------------------------------------


    //Metodo para ver eliminar usuarios ====================================
    // http://localhost:8111/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> elimninar (@PathVariable Long id){
        Optional<Usuario> optional = service.porId(id);
        if(optional.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.noContent().build();
    } // fin del metodo para eliminar usuarios
  //-------------------------------------------------



  // Metodo para listar todos los cursos y los usuarios
  // http://localhost:8111/usuarios-curso
  @GetMapping("/usuarios-curso")
  public ResponseEntity<?> obteberAlumnosCurso(@RequestParam List<Long> ids) {

        return ResponseEntity.ok(service.listarPorId(ids));
  }// fin del metodo de listar usuarios curso
 // --------------------------------------------


//Metdo para validar =======================================================
    private static ResponseEntity<Map<String, String>> getValidar(BindingResult result) {
        Map<String, String> errores = result.getFieldErrors().stream().collect(Collectors.toMap(err -> err.getField(), err -> "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }// fin del metodo para validar
   //--------------------------------

}// fin de usuario controller
