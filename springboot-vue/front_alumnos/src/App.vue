<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import swal from 'sweetalert2';


const alumnos = ref([]); // Definimos una variable reactiva para almacenar los alumnos
const nuevoAlumno = ref({
  nombre: '',
  apellidos: '',
  carrera: '',
  telefono: '',
  imagenURL: '',
});

const errores = ref({
  nombre: '',
  apellidos: '',
  telefono: ''
});
const editado = ref(false); // Variable para controlar si se está editando un alumno
const cargarAlumnos = async () => {
  const response = await axios.get('http://localhost:8081/alumnos/traer-alumnos');//traer todos los alumnos
  alumnos.value = response.data;
  console.log(alumnos.value);
}
const validarCampos = () =>{

  let valido = true;
  errores.value = {
    nombre: '',
    apellidos: '',
    telefono: ''
  };

  const soloLetras = /^[A-Za-zÁÉÍÓÚáéíóúñÑ\s]+$/;
  const soloNumeros = /^[0-9]{10}$/;

  if(!soloLetras.test(nuevoAlumno.value.nombre.trim())){
    swal.fire({
      icon: 'warning',
      text: 'Datos invalidos en Nombre',
      showConfirmButton: false,
      timer: 2000
    });
    nuevoAlumno.value.nombre = '';
    valido = false;
  }
  if(!soloLetras.test(nuevoAlumno.value.apellidos.trim())){
     swal.fire({
      icon: 'warning',
      text: 'Datos invalidos en Apellidos',
      showConfirmButton: false,
      timer: 2000
    });
    nuevoAlumno.value.apellidos = '';
    valido = false;

  }
  if(!soloNumeros.test(nuevoAlumno.value.telefono.trim())){
     swal.fire({
      icon: 'warning',
      text: 'Datos invalidos en Telefono',
      showConfirmButton: false,
      timer: 2000
    });
    errores.value.telefono = '';
    valido = false;
  }
  
  return valido;
 
}

const agregarAlumno = async () => {

  if(!validarCampos()){
    return;
  }
  if (editado.value) {
    // Si se está editando un alumno, actualizamos el alumno
    await axios.put(`http://localhost:8081/alumnos/editar-alumno/${nuevoAlumno.value.id}`, nuevoAlumno.value);
    editado.value = false; // Reiniciamos la variable de edición
    swal.fire({
      icon: 'success',
      title: 'Alumno Actualizado Correctamente',
      showConfirmButton: false,
      timer: 1500
    });
  } else {
    // Si no se está editando, agregamos un nuevo alumno
    await axios.post('http://localhost:8081/alumnos/insertar-alumnos', nuevoAlumno.value);
    swal.fire({
      icon: 'success',
      title: 'Alumno Agregado Correctamente',
      showConfirmButton: false,
      timer: 1500
    });
  }
  await cargarAlumnos(); // Recargamos la lista de alumnos después de agregar uno nuevo
  nuevoAlumno.value = { // Limpiamos el formulario
    nombre: '',
    apellidos: '',
    carrera: '',
    telefono: '',
    imagenURL: ''
  };


}
const editarAlumnos = (alumno) => {
  Object.assign(nuevoAlumno.value, alumno); // Asignamos los valores del alumno seleccionado al formulario
  editado.value = true; // Activamos el modo de edición
  
}
const eliminarAlumno = async (id) => {
  swal.fire({
    title: '¿Estás seguro de eliminar el alumno?',
    text: "¡No podrás revertir esto!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Sí, eliminarlo'
  }).then(async (result) => {
    if (result.isConfirmed) {
      await eliminarAlumnoPorId(id);
      swal.fire(
        'Eliminado!',
        'El alumno ha sido eliminado.',
        'success'
      )
    }
  })

}
const eliminarAlumnoPorId = async (id) => {
  try {
    await axios.delete(`http://localhost:8081/alumnos/eliminar-alumnos/${id}`);
    swal.fire({
        icon: 'success',
        title: 'Alumno Eliminado Correctamente',
        showConfirmButton: false,
        timer: 1500
    });
    console.log(`Alumno con ID ${id} eliminado`);
    await cargarAlumnos(); // Recargamos la lista de alumnos después de eliminar uno
  } catch (error) {
    console.error('Error al eliminar el alumno:', error);
    swal.fire({
      icon: 'error',
      title: 'Error al eliminar el alumno',
      text: 'No se pudo eliminar el alumno.',
    });
  }
}

onMounted(cargarAlumnos); // Llamamos a la función cargarAlumnos cuando el componente se monta


</script>

<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12 mt-4">
        <div class="card shadow p-4 mb-4">
          <h2 class="text-center">Formulario de Alumnos</h2>
          <form 
          @submit.prevent="agregarAlumno" >
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" maxlength="30" id="nombre" v-model="nuevoAlumno.nombre"
                  required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="apellidos" class="form-label">Apellidos</label>
                <input type="text" class="form-control" maxlength="30" id="apellidos" v-model="nuevoAlumno.apellidos"
                  required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="carrera" class="form-label">Carrera</label>
                <select id="carrera" class="form-select"  style="border-radius:8px; border: 2px solid #a78bfa;" required v-model="nuevoAlumno.carrera">
                  <option value="" disabled>Selecciona una Opción</option>
                  <option value="Ingenieria en Mecatronica">Ingenieria en Mecatronica</option>
                  <option value="Ingenieria en Gestion Empresarial">Ingenieria en Gestion Empresarial</option>
                  <option value="Ingenieria Industrial">Ingenieria Industrial</option>
                  <option value="Ingenieria Civil">Ingenieria Civil</option>
                  <option value="Ingenieria en Sistemas Computacionales">Ingenieria en Sistemas Computacionales</option>
                  <option value="Licenciatura en Contador Público">Licenciatura en Contador Público</option>
                  <option value="Licenciatura en Arquitectura">Licenciatura en Arquitectura</option>
                </select>

              </div>
              <div class="col-md-6 mb-3">
                <label for="telefono" class="form-label">Telefono</label>
                <input type="text" name="telefono" maxlength="10" class="form-control" id="telefono"
                  v-model="nuevoAlumno.telefono" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="imagenURL" class="form-label">Imagen URL</label>
                <input type="text" class="form-control" id="imagenURL" v-model="nuevoAlumno.imagenURL">
              </div>
            </div>
            <button type="submit" class="btn btn-primary">
              {{ editado ? 'Actualizar Alumno' : 'Agregar Alumno' }}
            </button>
          </form>
        </div>
      </div>

      <div class="col-md-12">
        <div class="card shadow">
          <div class="card-body">
            <h5 class="card-title mb-3">Tabla de Alumnos</h5>
            <table class="table table-hover allign-middle">
              <thead class="table-light">
                <tr>
                  <th scope="col">ID</th>
                  <th scope="col">Nombre</th>
                  <th scope="col">Apellidos</th>
                  <th scope="col">Carrera</th>
                  <th scope="col">Telefono</th>
                  <th scope="col">Imagen</th>
                  <th scope="col">Acciones</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="alumno in alumnos" :key="alumno.id">
                  <td>{{ alumno.id }}</td>
                  <td>{{ alumno.nombre }}</td>
                  <td>{{ alumno.apellidos }}</td>
                  <td>{{ alumno.carrera }}</td>
                  <td>{{ alumno.telefono }}</td>
                  <td><img :src="alumno.imagenURL" alt="Imagen de Alumno" width="50" height="50"></td>
                  <td>
                    <button @click=eliminarAlumno(alumno.id) class="btn btn-danger mx-2"><i
                        class="bi bi-trash2"></i></button>
                    <button @click=editarAlumnos(alumno) class="btn btn-warning"><i
                        class="bi bi-pencil-fill"></i></button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>
/* Fondo general */
.container {
  max-width: 1100px;
  margin: auto;
  padding: 30px 15px;
  background-color: #f3f4f6;
  border-radius: 15px;
}

/* Estilo de las tarjetas */
.card {
  border-radius: 16px;
  border: none;
  background-color: #ffffff;
  box-shadow: 0 4px 12px rgba(30, 58, 138, 0.1);
}

/* Títulos */
h2,
h5 {
  font-weight: bold;
  color: #1e3a8a;
  margin-bottom: 20px;
  text-align: center;
}

/* Etiquetas */
label {
  font-weight: 600;
  color: #374151;
}

/* Inputs */
input.form-control {
  border-radius: 10px;
  border: 2px solid #a78bfa;
  transition: 0.3s ease-in-out;
}
input.form-control:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 8px #a78bfa;
}

/* Botones */
button.btn {
  border-radius: 10px;
  font-weight: 600;
  margin-top: 5px;
  padding: 10px 18px;
  transition: all 0.3s ease;
}

.btn-primary {
  background-color: #3b82f6;
  border-color: #3b82f6;
  color: white;
}

.btn-primary:hover {
  background-color: #1e3a8a;
}

.btn-danger {
  background-color: #ef4444;
  border-color: #ef4444;
  color: white;
}

.btn-danger:hover {
  background-color: #b91c1c;
}

.btn-warning {
  background-color: #facc15;
  border-color: #facc15;
  color: #1e3a8a;
}

.btn-warning:hover {
  background-color: #eab308;
  color: white;
}

/* Tabla */
.table {
  background-color: #ffffff;
  border-radius: 12px;
  overflow: hidden;
}

.table th {
  background-color: #a78bfa;
  color: white;
  text-align: center;
  font-weight: 600;
}

.table td {
  text-align: center;
  vertical-align: middle;
}

/* Imagen en tabla */
table img {
  border-radius: 50%;
  object-fit: cover;
  width: 50px;
  height: 50px;
  border: 2px solid #3b82f6;
}

/* Hover en tabla */
.table-hover tbody tr:hover {
  background-color: #f0f0ff;
}
</style>
