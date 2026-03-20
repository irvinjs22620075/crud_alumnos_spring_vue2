package com.marco.alumnos.controller;
import com.marco.alumnos.model.Profesor;

import com.marco.alumnos.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesores")
@CrossOrigin(origins = "*")
public class ProfesorController {
    @Autowired
    private ProfesorRepository profesorRepository;
    //metodo get para obtener todos los alumnos de la base de datos
    @GetMapping("/traer-profesores")
    public List<Profesor> TraerProfesores() {
        return profesorRepository.findAll();

    }
    //metodo get para obtener un profesor por su id
    @GetMapping("/traer-profesor/{id}")
    public ResponseEntity<Profesor> TraerUnProfesor(@PathVariable Long id) {
        return profesorRepository.findById(id)
                .map(profesor -> ResponseEntity.ok(profesor))
                .orElse(ResponseEntity.notFound().build());
    }

    //metodo get para insertar un alumno en la base de datos
    @PostMapping("/insertar-profesores")
    public Profesor InsertarProfesor(@RequestBody Profesor profesor) {
        return profesorRepository.save(profesor);
    }
    //Metodo para eidtar un alumno de la base de datos
    @PutMapping("/editar-profesores/{id}")
    public ResponseEntity<Profesor> EditarProfesor(@PathVariable Long id,@RequestBody Profesor profesor) {
        return profesorRepository.findById(id).map(profesorExistente -> {
            profesorExistente.setNombre(profesor.getNombre());
            profesorExistente.setApellidos(profesor.getApellidos());
            profesorExistente.setEmail(profesor.getEmail());
            profesorExistente.setTelefono(profesor.getTelefono());
            profesorExistente.setRfc(profesor.getRfc());
            profesorExistente.setMateria(profesor.getMateria());
            profesorExistente.setImagenURL(profesor.getImagenURL());
            Profesor actualizado = profesorRepository.save(profesorExistente);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }
    //Metodo para eliminar un alumno de la base de datos
    @DeleteMapping("/eliminar-profesores/{id}")
    public void eliminarProfesor(@PathVariable Long id) {
        profesorRepository.deleteById(id);
    }

}
