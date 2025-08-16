// Variables globales
let empleados = [];
let centros = [];
let todosLosEmpleados = []; 
let paginaActual = 1;
const empleadosPorPagina = 5;
let idPersonaEliminar = null;


// Inicialización
document.addEventListener("DOMContentLoaded", function () {
  cargarEmpleados();
  cargarCentros();

  document
    .getElementById("formAgregarEmpleado")
    .addEventListener("submit", function (e) {
      e.preventDefault();
      agregarEmpleado();
    });

  document
    .getElementById("formEditarEmpleado")
    .addEventListener("submit", function (e) {
      e.preventDefault();
      editarEmpleado();
    });

  document
    .getElementById("formEliminarEmpleado")
    .addEventListener("submit", function (e) {
      e.preventDefault();
      eliminarEmpleado();
    });

  document.getElementById("buscar").addEventListener("keypress", function (e) {
    if (e.key === "Enter") buscarEmpleados();
  });

  document.querySelectorAll(".dropdown-item").forEach((item) => {
    item.addEventListener("click", function () {
      const texto = this.textContent.trim();
      const btnFiltro = document.querySelector(".botonOpcion");
      btnFiltro.textContent = texto;

     if (texto === "Todos") {
  empleados = [...todosLosEmpleados];
  paginaActual = 1;
  mostrarEmpleadosPorPagina();
  mostrarBotonesPaginacion();
     }
      else if (texto === "Activo") filtrarPorEstatus(1);
      else if (texto === "Inactivo") filtrarPorEstatus(0);
    });
  });
});

function cargarCentros() {
  fetch("http://localhost:8080/ProyectoHuellas/api/centros/getAll")
    .then((res) => res.json())
    .then((data) => {
      centros = data;
let opciones = `<option value="">Selecciona un centro</option>`;

      data.forEach((c) => {
        opciones +=  `<option value="${c.idCentro}">${c.nombreCentro}</option>`;
      });
    const centroModSelect = document.getElementById("centroMod");
      const centroAddSelect = document.getElementById("centroEmpleado");

      if (centroModSelect) centroModSelect.innerHTML = opciones;
      if (centroAddSelect) centroAddSelect.innerHTML = opciones;
      
      // No hay select centro en agregar, por eso no lo llenamos
    })
    .catch((error) => console.error("Error cargando centros:", error));
}

function cargarEmpleados() {
  fetch("http://localhost:8080/ProyectoHuellas/api/empleados/getAll")
    .then((res) => res.json())
    .then((data) => {
      empleados = data;
        todosLosEmpleados = [...data];
            empleados = data.filter(e => e.estatus === 1); // Guardamos una copia de todos los empleados
      paginaActual = 1;
      mostrarEmpleadosPorPagina();
      mostrarBotonesPaginacion();
    })
    .catch((error) => console.error("Error cargando empleados:", error));
}

function mostrarEmpleadosPorPagina() {
  const inicio = (paginaActual - 1) * empleadosPorPagina;
  const fin = inicio + empleadosPorPagina;
  const empleadosPagina = empleados.slice(inicio, fin);

  let filas = "";
  empleadosPagina.forEach((e) => {
    const foto = e.foto || "../img/fotoPrueba.jpg";
    const estatusClase = e.estatus === 1 ? "bg-success" : "bg-danger";
    const estatusTexto = e.estatus === 1 ? "Activo" : "Inactivo";

    filas += `
      <tr>
        <td class="mascotasCol"><span><img src="${foto}" alt="Foto">${e.nombre} ${e.app || ""}</span></td>
        <td><span class="activado ${estatusClase}"></span> ${estatusTexto}</td>
        <td>${e.genero || "No especificado"}</td>
        <td>${e.codigo || "N/A"}</td>
        <td>${e.rol || "No especificado"}</td>
        <td>${e.telefono || "N/A"}</td>
        <td>
          <button class="btn btn-sm btn-outline-secondary me-1" data-bs-toggle="modal" data-bs-target="#modEmpleado" onclick="cargarDatosEdicion(${e.idEmpleado})">
            <i class='bx bxs-edit'></i>
          </button>
          <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#eliminarEmpleado" onclick="prepararEliminacion(${e.idEmpleado}, '${e.nombre} ${e.app || ""}')">
            <i class='bx bx-trash'></i>
          </button>
          <button class="btn btn-sm btn-outline-info" data-bs-toggle="modal" data-bs-target="#infoEmpleado" onclick="mostrarInfoEmpleado(${e.idEmpleado})">
            <i class='bx bxs-info-circle'></i>
          </button>
        </td>
      </tr>
    `;
  });

  // Adaptación aquí: seleccionamos tbody sin id
  const tablaCuerpo = document.querySelector(".tablaGestiones tbody");
  if (tablaCuerpo) tablaCuerpo.innerHTML = filas;
}

function agregarEmpleado() {
  const form = document.getElementById("formAgregarEmpleado");
  const fotoFile = form.fotoEmpleado.files[0];

  if (!fotoFile) {
    mostrarAlerta("Debes seleccionar una foto", "fallo-empleado");
    return;
  }

  const reader = new FileReader();
  reader.onloadend = function () {
    const nuevoEmpleado = {
      nombre: form.nombreEmpleado.value.trim(),
      app: form.apellidoPatEmpleado.value.trim(),
      apm: form.apellidoMatEmpleado.value.trim(),
      fechaNacimiento: form.fechaNacEmpleado.value,
      genero: form.generoEmpleado.value,
      correo: form.correoEmpleado.value.trim(),
      contraseña: form.contrasenaEmpleado.value,
      foto: reader.result,
      telefono: form.telefonoEmpleado.value.trim(),
      direccion: form.direccionEmpleado.value.trim(),
      CP: form.cpEmpleado.value.trim(),
      rol: form.rolEmpleado.value,
      centro: form.centroEmpleado.value 
    };

    fetch("http://localhost:8080/ProyectoHuellas/api/empleados/insertar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(nuevoEmpleado),
    })
      .then((res) => res.json())
      .then(() => {
        form.reset();
        cargarEmpleados();
        bootstrap.Modal.getInstance(
          document.getElementById("agregarEmpleado")
        ).hide();
        mostrarExito("Empleado agregado correctamente");
      })
      .catch((error) => mostrarError("Error al agregar empleado", error));
  };
  reader.readAsDataURL(fotoFile);
}

function cargarDatosEdicion(idEmpleado) {
  const empleado = empleados.find((e) => e.idEmpleado == idEmpleado);
  if (!empleado) return;

  const form = document.getElementById("formEditarEmpleado");
  form.idPersonaMod.value = empleado.idPersona || "";

  form.idEmpleadoMod.value = empleado.idEmpleado || "";
  form.nombreEmpleadoMod.value = empleado.nombre || "";
  form.apellidoPatEmpleadoMod.value = empleado.app || "";
  form.apellidoMatEmpleadoMod.value = empleado.apm || "";
  form.fechaNacEmpleadoMod.value = empleado.fechaNacimiento || "";
  form.generoEmpleadoMod.value = empleado.genero || "";
  form.correoEmpleadoMod.value = empleado.correo || "";
  form.telefonoEmpleadoMod.value = empleado.telefono || "";
  form.direccionEmpleadoMod.value = empleado.direccion || "";
    form.contrasenaEmpleadoMod.value = empleado.contraseña || "";
  form.cpEmpleadoMod.value = empleado.CP || "";
  form.rolEmpleadoMod.value = empleado.rol || "";
  if (form.centroMod) form.centroMod.value = empleado.centro || "";
  const fotoVistaMod = document.getElementById("fotoEmpleadoVistaMod");
  if (fotoVistaMod) fotoVistaMod.src = empleado.foto || "../img/fotoPrueba.jpg";
}

function editarEmpleado() {
  const form = document.getElementById("formEditarEmpleado");
  const nuevaFotoFile = form.fotoEmpleadoMod.files[0];

  const empleado = {
    idPersona: form.idPersonaMod.value,
    idEmpleado: form.idEmpleadoMod.value,
    nombre: form.nombreEmpleadoMod.value.trim(),
    app: form.apellidoPatEmpleadoMod.value.trim(),
    apm: form.apellidoMatEmpleadoMod.value.trim(),
    fechaNacimiento: form.fechaNacEmpleadoMod.value,
    genero: form.generoEmpleadoMod.value,
    correo: form.correoEmpleadoMod.value.trim(),
    contraseña: form.contrasenaEmpleadoMod.value,
    telefono: form.telefonoEmpleadoMod.value.trim(),
    direccion: form.direccionEmpleadoMod.value.trim(),
    CP: form.cpEmpleadoMod.value.trim(),
    rol: form.rolEmpleadoMod.value,
    centro: form.centroMod ? form.centroMod.value : null,
  };

  const enviarEmpleado = (fotoFinal) => {
    empleado.foto = fotoFinal;

    fetch("http://localhost:8080/ProyectoHuellas/api/empleados/modificar", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(empleado),
    })
      .then((res) => res.json())
      .then(() => {
        cargarEmpleados();
        bootstrap.Modal.getInstance(document.getElementById("modEmpleado")).hide();
        mostrarExito("Empleado actualizado correctamente");
      })
      .catch((error) => mostrarError("Error al actualizar empleado", error));
  };

  if (nuevaFotoFile) {
    const reader = new FileReader();
    reader.onloadend = function () {
      enviarEmpleado(reader.result);
    };
    reader.readAsDataURL(nuevaFotoFile);
  } else {
  
    const empleadoExistente = empleados.find(e => e.idEmpleado == form.idEmpleadoMod.value);
    enviarEmpleado(empleadoExistente ? empleadoExistente.foto : null);
  }
}


function prepararEliminacion(idEmpleado, nombreCompleto) {
   const empleado = empleados.find(e => e.idEmpleado == idEmpleado);
  if (!empleado) return;

  idPersonaEliminar = empleado.idPersona; // AHORA guarda el idPersona
  document.getElementById("nombreEmpleadoEliminar").textContent = nombreCompleto;
}

function eliminarEmpleado() {
  if (!idPersonaEliminar) return;


 fetch(`http://localhost:8080/ProyectoHuellas/api/empleados/eliminar/${idPersonaEliminar}`, {

    method: "DELETE",
  }
)

    .then((res) => res.json())
    .then(() => {
      cargarEmpleados();
      bootstrap.Modal.getInstance(
        document.getElementById("eliminarEmpleado")
      ).hide();
      mostrarExito("Empleado eliminado correctamente");
    })
    .catch((error) => mostrarError("Error al eliminar empleado", error));
}

function mostrarAlerta(mensaje, idContenedor) {
  const contenedor = document.getElementById(idContenedor);
  if (!contenedor) return;
  const mensajeSpan = contenedor.querySelector(".mensaje-error");
  if (mensajeSpan) mensajeSpan.textContent = mensaje;
  contenedor.classList.remove("d-none");
  contenedor.classList.add("show");
}

function mostrarExito(mensaje) {
  alert(mensaje);
}

function mostrarError(mensaje, error) {
  console.error(mensaje, error);
  alert(mensaje);
}

function mostrarBotonesPaginacion() {
  const totalPaginas = Math.ceil(empleados.length / empleadosPorPagina);
  let botones = `
    <li class="page-item ${paginaActual === 1 ? "disabled" : ""}">
      <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual - 1})">&laquo;</a>
    </li>
  `;

  for (let i = 1; i <= totalPaginas; i++) {
    botones += `
      <li class="page-item ${paginaActual === i ? "active" : ""}">
        <a class="page-link" href="#" onclick="cambiarPagina(${i})">${i}</a>
      </li>
    `;
  }

  botones += `
    <li class="page-item ${paginaActual === totalPaginas ? "disabled" : ""}">
      <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual + 1})">&raquo;</a>
    </li>
  `;

  document.querySelector(".pagination").innerHTML = botones;
}

function cambiarPagina(pagina) {
  paginaActual = pagina;
  mostrarEmpleadosPorPagina();
}

function buscarEmpleados() {
  const textoBusqueda = document.getElementById("buscar").value.toLowerCase();
  if (!textoBusqueda) {
    empleados = [...todosLosEmpleados]; // <- restaurar todos
    paginaActual = 1;
    mostrarEmpleadosPorPagina();
    mostrarBotonesPaginacion();
    return;
  }

  const filtrados = todosLosEmpleados.filter(
    (e) =>
      e.nombre.toLowerCase().includes(textoBusqueda) ||
      e.app.toLowerCase().includes(textoBusqueda) ||
      (e.correo && e.correo.toLowerCase().includes(textoBusqueda)) ||
      (e.rol && e.rol.toLowerCase().includes(textoBusqueda))
  );

  empleados = filtrados;
  paginaActual = 1;
  mostrarEmpleadosPorPagina();
  mostrarBotonesPaginacion();
}


function filtrarPorEstatus(estatus) {
  const filtrados = todosLosEmpleados.filter((e) => e.estatus === estatus);
  empleados = filtrados;
  paginaActual = 1;
  mostrarEmpleadosPorPagina();
  mostrarBotonesPaginacion();
}


function mostrarInfoEmpleado(idEmpleado) {
  const empleado = empleados.find((e) => e.idEmpleado == idEmpleado);
  const centro = centros.find(c => c.idCentro == empleado.centro);

  if (!empleado) return;

  document.getElementById("infoFoto").src = empleado.foto || "../img/fotoPrueba.jpg";

  const nombreCompleto = `${empleado.nombre} ${empleado.app || ""} ${empleado.apm || ""}`.trim();

  document.getElementById("infoNombreEmpleado").textContent = nombreCompleto;

  // Asignar texto de estatus
  document.getElementById("infoEstatusTexto").textContent = empleado.estatus === 1 ? "Activo" : "Inactivo";

  // Asignar clase para color de estatus
  const estatusBadge = document.getElementById("infoEstatus");
  if (estatusBadge) {
    estatusBadge.classList.remove("bg-success", "bg-danger");
    if (empleado.estatus === 1) {
      estatusBadge.classList.add("bg-success");
    } else {
      estatusBadge.classList.add("bg-danger");
    }
  }

  document.getElementById("infoNombre").textContent = nombreCompleto;
  document.getElementById("infoFecha").textContent = empleado.fechaNacimiento || "No especificada";
  document.getElementById("infoGenero").textContent = empleado.genero || "No especificado";
  document.getElementById("infoTelefono").textContent = empleado.telefono || "N/A";
  document.getElementById("infoDireccion").textContent = empleado.direccion || "No especificada";
  document.getElementById("infoCodigoPostal").textContent = empleado.CP || "N/A";
  document.getElementById("infoCorreo").textContent = empleado.correo || "N/A";
  document.getElementById("infoCentro").textContent = centro ? centro.nombreCentro : "No asignado";
  document.getElementById("infoRolCabecera").textContent = empleado.rol || "No especificado";
  document.getElementById("infoRol").textContent = empleado.rol || "No especificado";
  document.getElementById("infoCodigo").textContent = empleado.codigo || "N/A";
}



// Funciones globales para acceso desde HTML
window.cargarDatosEdicion = cargarDatosEdicion;
window.prepararEliminacion = prepararEliminacion;
window.cambiarPagina = cambiarPagina;
window.buscarEmpleados = buscarEmpleados;
window.mostrarInfoEmpleado = mostrarInfoEmpleado;