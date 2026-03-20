package com.marco.alumnos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String Rfc;
    private String telefono;
    private String email;
    private String imagenURL;
    private String materia;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRfc() {
        return Rfc;
    }

    public void setRfc(String rfc) {
        Rfc = rfc;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }


    public String getApellidos() {
        return apellido;
    }

    public void setApellidos(String apellidos) {
        this.apellido = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImagenURL() {
        return imagenURL;
    }
    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }
}
