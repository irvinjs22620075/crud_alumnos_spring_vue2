package com.marco.alumnos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.alumnos.controller.ProfesorController;
import com.marco.alumnos.model.Profesor;
import com.marco.alumnos.repository.ProfesorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfesorController.class)
public class ProfesorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProfesorRepository profesorRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void debeTraerTodosLosProfesores() throws Exception {
        Profesor profesor1 = new Profesor();
        profesor1.setId(1L);
        profesor1.setNombre("Israel");
        profesor1.setEmail("Israelpop123@gmail.com");

        Profesor profesor2 = new Profesor();
        profesor2.setId(2L);
        profesor2.setNombre("Marco");
        profesor2.setEmail("marcoURGp123@gmail.com");

        when(profesorRepository.findAll())
                .thenReturn(Arrays.asList(profesor1,profesor2));

        mockMvc.perform(get("/profesores/traer-profesores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Israel")));
    }
    @Test
    public void debeInsertarUnDocente() throws Exception{
        Profesor docenteNuevo = new Profesor();
        docenteNuevo.setNombre("Pedro");
        docenteNuevo.setMateria("Desarrollo Agil");

        when(profesorRepository.save(org.mockito.ArgumentMatchers.any(Profesor.class))).thenReturn(docenteNuevo);

        mockMvc.perform(post("/profesores/insertar-profesores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(docenteNuevo)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is ("Pedro")));
        //.andExpect(jsonPath("$.nombre", is("Harry")));
    }

    @Test
    public void debeEliminarUnDocente() throws Exception{

        Long idParaEliminar = 1L;

        mockMvc.perform(delete("/profesores/eliminar-profesores/{id}", idParaEliminar)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(profesorRepository, times( 1)).deleteById(idParaEliminar);

    }

    }
