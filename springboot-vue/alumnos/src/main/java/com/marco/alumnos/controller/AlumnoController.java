package com.marco.alumnos.controller;

import com.marco.alumnos.services.AlumnoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.marco.alumnos.model.Alumno;
import com.marco.alumnos.repository.AlumnoRepository;

@RestController
@RequestMapping("/alumnos")
@CrossOrigin(origins = "*")
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;
    @GetMapping("/traer-alumnos")
    public  List <Alumno> traerAlumos(){
        return alumnoService.obtenerTodos();
    }
    @GetMapping("/traer-alumno/{id}")
    public ResponseEntity<Alumno> traerUnAlumno (@PathVariable Long id){
        Optional<Alumno> alumno = alumnoService.obtenerPorId(id);
        return alumno.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/insertar-alumnos")
    public Alumno insertarAlumno(@RequestBody Alumno alumno){
        return alumnoService.guardarAlumno(alumno);
    }
    @PutMapping("/editar-alumno/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumno){
        Optional<Alumno> actualizado = alumnoService.actualizaralumno(id,alumno);
        return actualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping ("/eliminar-alumnos/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id){
        alumnoService.eliminarAlumno(id);
        return  ResponseEntity.ok().build();
    }


}
