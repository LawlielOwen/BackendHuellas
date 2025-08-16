let solicitudes = [];
let animales = [];
let adoptantes = [];
let idsolicitud = null;
let idEliminar = null;
let paginaActual = 1;
const animalesPorPagina = 5;
document.addEventListener("DOMContentLoaded", async function () {
  await cargarAdoptante();
  await cargarMascotas();
  cargarSolicitudes();
  cargarContadores();
  document.getElementById("Todos").addEventListener("click", () => cargarTodas());
  document.getElementById("Aceptada").addEventListener("click", () => filtrarPorEstatus(1));
  document.getElementById("Pendiente").addEventListener("click", () => filtrarPorEstatus(2));
  document.getElementById("Rechazada").addEventListener("click", () => filtrarPorEstatus(3));
});
function mostrarAnimalesPorPagina() {
  const inicio = (paginaActual - 1) * animalesPorPagina;
  const fin = inicio + animalesPorPagina;
  const animalesPagina = animales.slice(inicio, fin);

  let filas = "";
  animalesPagina.forEach(a => {
    const foto = a.foto || "img/default.jpg";
    filas += `
            <tr>
                <td class="mascotasCol"><span><img src="${foto}" alt="Foto de mascota">${a.nombreAnimal}</span></td>
                <td><span class="activado ${a.estatus === 1 ? 'bg-success' : a.estatus === 2 ? 'bg-warning' : ''}"></span> ${a.estatusTexto}</td>
                <td>${a.genero}</td>
                <td>${a.edad}</td>
                <td>${a.codigoAnimal}</td>
                <td>${a.raza}</td>
                <td>${a.peso} kg</td>
                <td>${a.especie}</td>
                <td>
                    <button class="btn btn-sm btn-outline-secondary me-1" data-bs-toggle="modal"
                        data-bs-target="#modAnimal" onclick="cargarAnimal(${a.idAnimal})">
                        <i class='bx bxs-edit'></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal"
                        data-bs-target="#eliminarAnimal" onclick="prepararEliminar(${a.idAnimal})">
                        <i class='bx bx-trash'></i>
                    </button>
                    <button class="btn btn-sm btn-outline-info" data-bs-toggle="modal"
                        data-bs-target="#infoAnimal" onclick="mostrarInfoAnimal(${a.idAnimal})">
                        <i class='bx bxs-info-circle'></i>
                    </button>
                </td>
            </tr>
        `;
  });

  document.getElementById("cuerpoTabla").innerHTML = filas;
}
function mostrarBotonesPaginacion() {
  const totalPaginas = Math.ceil(animales.length / animalesPorPagina);
  let botones = "";

  botones += `
        <li class="page-item ${paginaActual === 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual - 1})">Anterior</a>
        </li>
    `;

  for (let i = 1; i <= totalPaginas; i++) {
    botones += `
            <li class="page-item ${paginaActual === i ? 'active' : ''}">
                <a class="page-link" href="#" onclick="cambiarPagina(${i})">${i}</a>
            </li>
        `;
  }

  botones += `
        <li class="page-item ${paginaActual === totalPaginas ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual + 1})">Siguiente</a>
        </li>
    `;

  document.querySelector(".pagination").innerHTML = botones;
}
function cambiarPagina(pagina) {
  const totalPaginas = Math.ceil(animales.length / animalesPorPagina);
  if (pagina >= 1 && pagina <= totalPaginas) {
    paginaActual = pagina;
    mostrarAnimalesPorPagina();
    mostrarBotonesPaginacion();
  }
}
function cargarSolicitudes() {
  fetch("/api/solicitudes/getAll")
    .then(res => res.json())
    .then(data => {
      solicitudes = data;
      paginaActual = 1;
      mostrarAnimalesPorPagina();
      mostrarBotonesPaginacion();

    });
}
async function cargarMascotas() {
  const res = await fetch("/api/mascotas/getAll");
  const data = await res.json();
  animales = data;
}
function mostrarAnimalesPorPagina() {
  const inicio = (paginaActual - 1) * animalesPorPagina;
  const fin = inicio + animalesPorPagina;
  const animalesPagina = solicitudes.slice(inicio, fin);

  let filas = "";
  animalesPagina.forEach(soli => {
    const animal = animales.find(a => a.idAnimal === soli.idAnimal);
    
    filas += `
          <tr>
            <td class="mascotasCol">
              <span>
                <img src="${animal.foto}" alt="Foto de ${animal.nombreAnimal}" style="width: 50px; height: 50px; object-fit: cover;">
                ${animal.nombreAnimal}
              </span>
            </td>
            <td><span class="activado  bg-${soli.estatus === 1 ? 'success' : soli.estatus === 2 ? 'warning' : 'danger'}"></span> 
                ${soli.estatus === 1 ? 'Aceptada' : soli.estatus === 2 ? 'Pendiente' : 'Rechazada'}
            </td>
            <td>${soli.fecha}</td>
            <td>${soli.nombreAdoptante}</td>
           
            <td>
              <button class="btn btn-sm btn-outline-info" data-bs-toggle="modal" data-bs-target="#revisarSolicitud" onclick="mostrarSolicitud(${soli.idSolicitud})">
                <i class='bx bxs-info-circle'></i>
              </button>
              <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#eliminarSolicitud" onclick="elimacionCarga(${soli.idSolicitud})">
                <i class='bx bx-trash'></i>
              </button>
            </td>
          </tr>
        `;
  });

  document.getElementById("cuerpoTabla").innerHTML = filas;
}
function mostrarBotonesPaginacion() {
  const totalPaginas = Math.ceil(solicitudes.length / animalesPorPagina);
  let botones = "";

  botones += `
        <li class="page-item ${paginaActual === 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual - 1})">Anterior</a>
        </li>
    `;

  for (let i = 1; i <= totalPaginas; i++) {
    botones += `
            <li class="page-item ${paginaActual === i ? 'active' : ''}">
                <a class="page-link" href="#" onclick="cambiarPagina(${i})">${i}</a>
            </li>
        `;
  }

  botones += `
        <li class="page-item ${paginaActual === totalPaginas ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual + 1})">Siguiente</a>
        </li>
    `;

  document.querySelector(".pagination").innerHTML = botones;
}
function cambiarPagina(pagina) {
  const totalPaginas = Math.ceil(animales.length / animalesPorPagina);
  if (pagina >= 1 && pagina <= totalPaginas) {
    paginaActual = pagina;
    mostrarAnimalesPorPagina();
    mostrarBotonesPaginacion();
  }
}
async function cargarAdoptante() {
  const res = await fetch("/api/adoptante/getAll");
  const data = await res.json();
  adoptantes = data;
}

function mostrarSolicitud(idSolicitud) {
  const solicitud = solicitudes.find(s => s.idSolicitud === idSolicitud);
  if (!solicitud) return;
  idsolicitud = solicitud.idSolicitud;
  idAnimalSeleccionado = solicitud.idAnimal;

  const animal = animales.find(a => a.idAnimal === solicitud.idAnimal);
  const adoptante = adoptantes.find(d => d.idAdoptante === solicitud.idAdoptante);


  const estatusTexto = solicitud.estatus === 1 ? "Aprobada" :
    solicitud.estatus === 2 ? "Pendiente" :
      solicitud.estatus === 3 ? "Rechazada" : "Desconocido";

  const estatusColor = solicitud.estatus === 1 ? "bg-success" :
    solicitud.estatus === 2 ? "bg-warning" :
      solicitud.estatus === 3 ? "bg-danger" : "bg-secondary";

  const informacion = ` 
    <div class="modal-header" style="background-color: #A4A17E; color: white; border-bottom: 3px solid #8a8869;">
      <h5 class="modal-title d-flex align-items-center gap-2" id="revisarSolicitudLabel">
        <i class='bx bxs-file-doc fs-4'></i> Detalles de Solicitud
      </h5>
      <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
    </div>
     
    <div class="modal-body" style="background-color: #FFF8F2;">
      <div class="row">
        <!-- Columna izquierda - Información principal -->
        <div class="col-md-6">
          <div class="card mb-3 border-0 shadow-sm">
            <div class="card-header text-white fw-bold" style="background-color: #A4A17E;">
              <i class='bx bx-info-circle me-2'></i>Información Principal
            </div>
            <div class="card-body">
              <div class="mb-3">
                <label class="form-label text-muted small">Mascota:</label>
                <p class="fw-bold text-dark" id="solicitudMascota">
                  <i class='bx bxs-dog me-2'></i>${solicitud.nombreAnimal}
                </p>
              </div>
              <div class="mb-3">
                <label class="form-label text-muted small">Adoptante:</label>
                <p class="fw-bold text-dark" id="solicitudAdoptante">
                  <i class='bx bx-user me-2'></i>${solicitud.nombreAdoptante}
                </p>
              </div>
              <div class="mb-3">
                <label class="form-label text-muted small">Fecha:</label>
                <p class="fw-bold text-dark" id="solicitudFecha">
                  <i class='bx bx-calendar me-2'></i>${solicitud.fecha}
                </p>
              </div>
              <div class="mb-3">
                <label class="form-label text-muted small">Estatus:</label>
                <p>
                  <span class="badge ${estatusColor} py-2 px-3 rounded-pill" id="solicitudEstatus">
                    <i class='bx ${solicitud.estatus === 1 ? 'bx-check' : solicitud.estatus === 3 ? 'bx-x' : 'bx-time'} me-1'></i>
                    ${estatusTexto}
                  </span>
                </p>
              </div>
            </div>
          </div>
        </div>
        
        <!-- Columna derecha - Datos de contacto -->
        <div class="col-md-6">
          <div class="card mb-3 border-0 shadow-sm">
            <div class="card-header text-white fw-bold" style="background-color: #A4A17E;">
              <i class='bx bx-contact me-2'></i>Datos de Contacto
            </div>
            <div class="card-body">
              <div class="mb-3">
                <label class="form-label text-muted small">Teléfono:</label>
                <p class="fw-bold text-dark" id="solicitudContacto">
                  <i class='bx bx-phone me-2'></i>${solicitud.telefono}
                </p>
              </div>
              <div class="mb-3">
                <label class="form-label text-muted small">Correo:</label>
                <p class="fw-bold text-dark" id="solicitudCorreo">
                  <i class='bx bx-envelope me-2'></i>${solicitud.correo}
                </p>
              </div>
              <div class="mb-3">
                <label class="form-label text-muted small">Dirección:</label>
                <p class="fw-bold text-dark" id="solicitudDireccion">
                  <i class='bx bx-map me-2'></i>${solicitud.direccion}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Motivo de adopción -->
      <div class="card border-0 shadow-sm mb-3">
        <div class="card-header text-white fw-bold" style="background-color: #A4A17E;">
          <i class='bx bx-edit me-2'></i>Motivo de Adopción
        </div>
        <div class="card-body" style="max-height: 200px; overflow-y: auto;">
          <p class="mb-0" style="white-space: pre-line;">${solicitud.motivo}</p>
        </div>
      </div>
    </div>

    <!-- Pie del modal - Botones de acción -->
    <div class="modal-footer d-flex align-items-center justify-content-center" style="background-color: #FFF8F2; border-top: 1px solid #e0e0e0;">
      ${solicitud.estatus === 2 ? `
        <button type="button" class="btn btn-success px-4 py-2 fw-bold" id="btnAceptar" onclick="aceptarSolicitud()" style="background-color: #5cb85c; border: none;">
          <i class='bx bx-check-circle me-2'></i>Aprobar
        </button>
        <button type="button" class="btn btn-danger px-4 py-2 fw-bold" id="btnRechazar" onclick="rechazarSolicitud()" style="background-color: #d9534f; border: none;">
          <i class='bx bx-x-circle me-2'></i>Rechazar
        </button>
      ` : ''}
      <button type="button" class="btn btn-dark px-4 py-2 fw-bold" data-bs-dismiss="modal" style="background-color: #000000; border: none;">
        <i class='bx bx-door-open me-2'></i>Cerrar
      </button>
    </div>
  `;

  document.getElementById("informacion").innerHTML = informacion;
}
function aceptarSolicitud() {
  const boton = document.getElementById("btnAceptar");
  const textoOriginal = boton.innerHTML;
  boton.innerHTML = `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Aceptando...`;
  boton.disabled = true;
  const soli = {
    idSolicitud: idsolicitud,
    idAnimal: idAnimalSeleccionado
  };
  fetch("/api/solicitudes/aceptarSoli", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(soli)
  })
    .then(res => res.json())
    .then(() => {
      boton.innerHTML = textoOriginal;
      boton.disabled = false;
      cargarSolicitudes();
      cargarContadores();
      const modal = bootstrap.Modal.getInstance(document.getElementById('revisarSolicitud'));
      modal.hide();
    })

}
function rechazarSolicitud() {
  const boton = document.getElementById("btnRechazar");
  const textoOriginal = boton.innerHTML;
  boton.innerHTML = `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Rechazando...`;
  boton.disabled = true;
  const soli = {
    idSolicitud: idsolicitud
  };
  fetch("/api/solicitudes/rechazarSoli", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(soli)
  })
    .then(res => res.json())
    .then(() => {
      boton.innerHTML = textoOriginal;
      boton.disabled = false;
      cargarSolicitudes();
      cargarContadores();
      const modal = bootstrap.Modal.getInstance(document.getElementById('revisarSolicitud'));
      modal.hide();
    })

}
function cargarContadores() {

  fetch("/api/solicitudes/contarDisponibles")
    .then(res => res.json())
    .then(num => {
      document.getElementById("contadorPendientes").innerHTML = num;
    })

  fetch("/api/solicitudes/contarAceptadas")
    .then(res => res.json())
    .then(num => {
      document.getElementById("contadorAceptadas").innerHTML = num;
    })

  fetch("/api/solicitudes/contarRechazadas")
    .then(res => res.json())
    .then(num => {
      document.getElementById("contadorRechazadas").innerHTML = num;
    })
}
function filtrarPorEstatus(estatus) {
  fetch("/api/solicitudes/filtroEstatus", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ estatus: estatus })
  })
    .then(res => res.json())
    .then(data => {
      solicitudes = data;
      actualizarTabla(data);
    });
}
function actualizarTabla(data) {
   paginaActual = 1;
    mostrarAnimalesPorPagina();
    mostrarBotonesPaginacion();
  let filas = "";
  data.forEach(a => {
    const animal = animales.find(an => an.idAnimal === a.idAnimal);

    filas += `
          <tr>
            <td class="mascotasCol">
              <span>
                <img src="${animal.foto}" alt="Foto de ${animal.nombreAnimal}" style="width: 50px; height: 50px; object-fit: cover;">
                ${animal.nombreAnimal}
              </span>
            </td>
            <td><span class="activado  bg-${a.estatus === 1 ? 'success' : a.estatus === 2 ? 'warning' : 'danger'}"></span> 
                ${a.estatus === 1 ? 'Aceptada' : a.estatus === 2 ? 'Pendiente' : 'Rechazada'}
            </td>
            <td>${a.fecha}</td>
            <td>${a.nombreAdoptante}</td>
            <td>
              <button class="btn btn-sm btn-outline-info" data-bs-toggle="modal" data-bs-target="#revisarSolicitud" onclick="mostrarSolicitud(${a.idSolicitud})">
                <i class='bx bxs-info-circle'></i>
              </button>
              <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#eliminarSolicitud" onclick="elimacionCarga(${a.idSolicitud})">
                <i class='bx bx-trash'></i>
              </button>
            </td>
          </tr>
        `;
  });
  document.getElementById("cuerpoTabla").innerHTML = filas;
}
document.getElementById("buscar").addEventListener("keydown", function (e) {
  if (e.key === "Enter") {
    const termino = this.value.trim();

    if (termino === "") {
      cargarSolicitudes();
    } else {
      buscarSolicitudPorNombre(termino);
    }
  }
});
function buscarSolicitudPorNombre(nombre) {
  fetch("/api/solicitudes/buscarSoli", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ nombreAnimal: nombre, nombreAdoptante: nombre })
  })
    .then(res => res.json())
    .then(data => {
      actualizarTabla(data);
    })
    ;
}
async function cargarTodas() {

  const res = await fetch("/api/solicitudes/getTodas");
  const data = await res.json();
  solicitudes = data;
  actualizarTabla(data);

}
function elimacionCarga(id) {
  
  idEliminar = id;
}
function eliminarSolicitud() {

  const soli = {
    idSolicitud: idEliminar
  };
  fetch("/api/solicitudes/eliminarSoli", {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(soli)
  })
    .then(res => res.json())
    .then(() => {
      cargarSolicitudes();
      cargarContadores();
    })

}