package com.marco.alumnos.services;

import com.marco.alumnos.model.Alumno;
import com.marco.alumnos.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> obtenerTodos(){
        return alumnoRepository.findAll();
    }
    public Optional<Alumno> obtenerPorId(Long id){
        return alumnoRepository.findById(id);
    }
    public Alumno guardarAlumno(Alumno alumno){
        return alumnoRepository.save(alumno);
    }

    public Optional<Alumno> actualizaralumno (Long id, Alumno alumnoDetalles){
        return alumnoRepository.findById(id).map(alumnoExistene ->{
           alumnoExistene.setNombre(alumnoDetalles.getNombre());
           alumnoExistene.setApellido(alumnoDetalles.getApellido());
           alumnoExistene.setEmail(alumnoDetalles.getEmail());
           alumnoExistene.setNumeroControl(alumnoDetalles.getNumeroControl());
           alumnoExistene.setTelefono(alumnoDetalles.getTelefono());
           alumnoExistene.setCarrera(alumnoDetalles.getCarrera());
           alumnoExistene.setImagenURL(alumnoDetalles.getImagenURL());
           return alumnoRepository.save(alumnoExistene);
        });
    }
    public void eliminarAlumno(Long id){
        alumnoRepository.deleteById(id);
    }
}
