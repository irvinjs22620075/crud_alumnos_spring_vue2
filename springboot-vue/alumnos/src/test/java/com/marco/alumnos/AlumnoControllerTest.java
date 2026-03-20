package com.marco.alumnos;

import com.marco.alumnos.services.AlumnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.alumnos.controller.AlumnoController;
import com.marco.alumnos.model.Alumno;
import com.marco.alumnos.repository.AlumnoRepository;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.Arrays;

import org.springframework.http.MediaType;

@WebMvcTest(AlumnoController.class)
public class AlumnoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AlumnoService alumnoService;
    @Autowired
    private ObjectMapper objectMapper;

    
    @Test
    public void debeTraerTodosLosAlumnos() throws Exception {
        Alumno alumno1 = new Alumno();
        alumno1.setId(1L);
        alumno1.setNumeroControl("226201119");
        alumno1.setNombre("Pedro");
        alumno1.setApellido("Garcia");
        alumno1.setCarrera("Sistemas");
        alumno1.setEmail("pedropop123@gmail.com");
        alumno1.setImagenURL("https://imgs.search.brave.com/TGXkqE8ZY81Q7arPe2yP2yIHk9utjxEWahrtfytdZ2w/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9wMS5o/aWNsaXBhcnQuY29t/L3ByZXZpZXcvMzA5/LzIxNy8xMzAvc3Vw/ZXItbWFyaW8tbmVz/LWRvY2staWNvbnMt/c2h5LWd1eS1wbmct/aWNvbi10aHVtYm5h/aWwuanBn");


        Alumno alumno2 = new Alumno();
        alumno2.setId(2L);
        alumno2.setNombre("Pablo");
        alumno2.setApellido("Velasco");
        alumno2.setCarrera("Civil");
        alumno2.setNumeroControl("22620001");
        alumno2.setEmail("pablopablito@gmail.com");
        alumno2.setImagenURL("https://imgs.search.brave.com/FKoYv89Ty7Z-mE0BKI6ywvCbPMecexnTqLlcbhTUOwc/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9wMS5o/aWNsaXBhcnQuY29t/L3ByZXZpZXcvMTA2/LzgyNi80OTAvbnVl/dm8tZGUtbmVzLWRl/LWJtby1hZHZlbnR1/cmUtdGltZS1ibW8t/aWxsdXN0cmF0aW9u/LXBuZy1jbGlwYXJ0/LXRodW1ibmFpbC5q/cGc");

        when(alumnoService.obtenerTodos())
                .thenReturn(Arrays.asList(alumno1, alumno2));

        mockMvc.perform(get("/alumnos/traer-alumnos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].numeroControl", is("22620001")));
    }

    @Test
    public void debeInsertarUnAlumno() throws Exception{
        Alumno alumnoNuevo = new Alumno();
        alumnoNuevo.setId(1L);
        alumnoNuevo.setNombre("Federico");
        alumnoNuevo.setCarrera("Industrial");
        alumnoNuevo.setTelefono("9531172310");

        when(alumnoService.guardarAlumno(org.mockito.ArgumentMatchers.any(Alumno.class))).thenReturn(alumnoNuevo);

        mockMvc.perform(post("/alumnos/insertar-alumnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumnoNuevo)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is ("Federico")));
    }
    @Test
    public void debeEditarUnAlumno() throws Exception {

        Alumno alumnoExistente = new Alumno();
        alumnoExistente.setId(1L);
        alumnoExistente.setNombre("Pedro");
        alumnoExistente.setCarrera("Sistemas");
    }

    @Test
    public void debeEliminarUnDocente() throws Exception{

        Long idParaEliminar = 1L;

        mockMvc.perform(delete("/alumnos/eliminar-alumnos/{id}", idParaEliminar)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(alumnoService, times( 1)).eliminarAlumno(idParaEliminar);

    }

}
