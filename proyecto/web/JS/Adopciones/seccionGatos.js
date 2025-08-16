animales = [];
let centros = [];
let paginaActual = 1;
const animalesPorPagina = 6;
document.addEventListener("DOMContentLoaded", function () {
  cargarMascotas();
  cargarCentros();
});
function cargarCentros() {
  fetch("http://localhost:8080/ProyectoHuellas/api/centros/getAll")
    .then(res => res.json())
    .then(data => {
      centros = data;
    });
}
function cargarMascotas() {
  mostrarCarga();
  fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/getGatos")
    .then(res => res.json())
    .then(data => {
      animales = data;
      paginaActual = 1;
      ocultarCarga();
      mostrarPagina(animales);
    
    });
}
function mostrarPagina(listaAnimales) {
  const contenedor = document.getElementById("contenedorCartas");
  contenedor.innerHTML = "";

  const inicio = (paginaActual - 1) * animalesPorPagina;
  const fin = inicio + animalesPorPagina;
  const pagina = listaAnimales.slice(inicio, fin);

  if (pagina.length === 0) {
    contenedor.innerHTML = `<p class="text-center w-100">No se encontraron resultados.</p>`;
    return;
  }

  pagina.forEach(a => {
    contenedor.innerHTML += `
      <div class="col">
        <div class="card cartaAnimal">
          <img src="${a.foto}" class="card-img-top" alt="Foto de ${a.nombreAnimal}">
          <div class="card-body">
            <p class="card-text categoria">${a.especie}</p>
            <div class="info">
              <h5 class="card-title fw-bold">${a.nombreAnimal}</h5>
              <div class="row">
                <div class="col-6">
                  <p class="card-text nowrap">Edad: ${a.edad}</p>
                </div>
                <div class="col-6">
                  <p class="card-text nowrap">Sexo: ${a.genero}</p>
                </div>
              </div>
              <p class="card-text mt-1">Carácter: ${a.caracter}</p>
            </div>
            <div class="text-center">
              <button class="btn button-carta" data-bs-toggle="modal" data-bs-target="#info"
                onclick="mostrarInfo(${a.idAnimal})">
                Adóptame 🐾
              </button>
            </div>
          </div>
        </div>
      </div>`;
  });

  mostrarControlesPaginacion(listaAnimales.length);
}
function mostrarControlesPaginacion(totalAnimales) {
  const totalPaginas = Math.ceil(totalAnimales / animalesPorPagina);
  const paginacion = document.getElementById("paginacion");

  let html = `
    <nav aria-label="Paginación de animales">
      <ul class="pagination">
        <li class="page-item ${paginaActual === 1 ? 'disabled' : ''}">
          <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual - 1})">Anterior</a>
        </li>`;

  for (let i = 1; i <= totalPaginas; i++) {
    html += `
      <li class="page-item ${i === paginaActual ? 'active' : ''}">
        <a class="page-link" href="#" onclick="cambiarPagina(${i})">${i}</a>
      </li>`;
  }

  html += `
        <li class="page-item ${paginaActual === totalPaginas ? 'disabled' : ''}">
          <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual + 1})">Siguiente</a>
        </li>
      </ul>
    </nav>`;

  paginacion.className = "pagination-container";
  paginacion.innerHTML = html;
}
function cambiarPagina(pagina) {
  const totalPaginas = Math.ceil(animales.length / animalesPorPagina);
  if (pagina >= 1 && pagina <= totalPaginas) {
    paginaActual = pagina;
    mostrarPagina(animales);
  }
}
function mostrarInfo(id) {
  const animal = animales.find(a => a.idAnimal === id);
  if (!animal) return;
  const centro = centros.find(c => c.idCentro === animal.idCentro);
  const informacion = `
     <div class="modal-dialog modal-dialog-centered modal-lg m-0">
  <div class="modal-content" style="border: none;">
   <div class="modal-header text-white headerModal " style=" border-bottom: none; padding: 1rem;" >
                <h5 class="modal-title d-flex align-items-center gap-2 text-center">
                    <i class='bx bxs-dog fs-4'></i> Información Adicional
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

    <div class="modal-body p-0" style="background-color: #FFF8F2;">
      <div class="row g-0" style="min-height: fit-content;">
     
         <div class="col-md-4 mb-4 mb-md-0 p-3">
                        <img src="${animal.foto}" alt="${animal.nombreAnimal}" 
                            class="img-fluid rounded shadow w-100" 
                            style="height: 220px; object-fit: cover; border: 3px solid #A4A17E;  margin-top: 0.5rem;">
                        <div class="text-center mt-3">
                            <h3 class="fw-bold mb-3" style="color: #000000;">${animal.nombreAnimal}</h3>
                            <div class="d-flex justify-content-center gap-2 align-items-center flex-wrap">
                                <span class="badge py-2 px-3 rounded-pill" style="background-color: #A4A17E; color: white;">
                                    <i class='bx bx-body me-1'></i> <span>${animal.tamano}</span>
                                </span>
                            </div>
                        </div>
                    </div>
        
        <div class="col-md-8 p-4 d-flex flex-column">
          <div class="card mb-3 border-0 shadow-sm">
            <div class="card-header bg-white p-3 text-muted">
              <i class='bx bx-info-circle me-2' style="color: #A4A17E;"></i>Información Principal
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col-md-6">
                  <p class="mt-2">
                    <span class="fw-semibold text-secondary">Género / Edad:</span><br>
                    <span class="text-dark small">
                      <i class='bx bx-${animal.genero === 'Hembra' ? 'female' : 'male'}'></i> ${animal.genero} / 
                      <i class='bx bx-calendar-heart'></i> ${animal.edad} 
                    </span>
                  </p>
                  <p class="mt-2">
                    <span class="fw-semibold text-secondary">Raza:</span><br>
                    <span class="text-dark small"><i class="bx bx-shield"></i> ${animal.raza}</span>
                  </p>
                </div>
                <div class="col-md-6">
                  <p class="mt-2">
                    <span class="fw-semibold text-secondary">Peso:</span><br>
                    <span class="text-dark small"><i class="bx bx-line-chart"></i> ${animal.peso} kg</span>
                  </p>
                  <p class="mb-2">
                    <span class="fw-semibold text-secondary">Centro:</span><br>
                    <span class="text-dark small"><i class="bx bx-home-alt"></i> ${centro ? centro.nombreCentro : 'No disponible'}</span>
                  </p>
                </div>
              </div>
            </div>
          </div>

          <div class="bg-white p-3 rounded shadow-sm mb-3" style="overflow-y: auto;">
            <div class="d-flex align-items-center gap-2 mb-2">
              <i class='bx bx-notepad' style="color: #A4A17E;"></i>
              <label class="form-label text-muted small mb-0">Descripción</label>
            </div>
            <div class="border-top pt-2">
              <p class="mb-0 small text-dark" style="white-space: pre-line; ">${animal.descripcion}</p>
            </div>
          </div>
        
          <div class="d-flex flex-wrap gap-2 mt-auto">
            <button class="btn  px-4 py-2 rounded-3 d-flex align-items-center gap-2 shadow-sm " style="background: linear-gradient(135deg, #A4A17E, #8a8869); color: white;" data-bs-dismiss="modal"
              onclick="revisarSesion(${animal.idAnimal})">
              <i class='bx bxs-heart'></i> Adóptame
            </button>
            <button class="btn btn-dark px-4 py-2 rounded-3 d-flex align-items-center gap-2 shadow-sm" data-bs-dismiss="modal">
              <i class='bx bx-x-circle'></i> Cerrar
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
`;

  document.getElementById("informacion").innerHTML = informacion;
}
document.getElementById("buscar").addEventListener("keydown", function (e) {
  if (e.key === "Enter") {
    const termino = this.value.trim();

    if (termino === "") {
      cargarMascotas();
    } else {
      buscarMascotasPorNombre(termino);
    }
  }
});
function buscarMascotasPorNombre(nombre) {
  fetch("http://localhost:8080/ProyectoHuellas/api/inicio/buscarGatos", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ nombreAnimal: nombre })
  })
    .then(res => res.json())
    .then(data => {

      actualizarTabla(data);
    })
    ;
}
function actualizarTabla(animalesFiltrados) {
  animales = animalesFiltrados;
  paginaActual = 1;
  mostrarPagina(animales);
}
function aplicarFiltros() {
  const genero = document.getElementById("filtroSexo").value;
  const edad = document.getElementById("filtroedad").value;
  const caracter = document.getElementById("filtroCaracter").value;
  fetch("http://localhost:8080/ProyectoHuellas/api/inicio/filtroGatos", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      genero: genero,
      edad: edad,
      caracter: caracter
    })
  })
    .then(res => res.json())
    .then(data => {

      actualizarTabla(data);
    })
    ;
}
function aplicarFiltrosResponsive() {
  const btn = document.getElementById("btnAplicarFiltros");
  const originalText = btn.innerText;
  btn.innerText = "Filtrando...";
  btn.disabled = true;
  const genero = document.getElementById("filtroSexorRes").value;
  const edad = document.getElementById("filtroedadRes").value;
  const caracter = document.getElementById("filtroCaracterRes").value;
  fetch("http://localhost:8080/ProyectoHuellas/api/inicio/filtroGatos", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      genero: genero,
      edad: edad,
      caracter: caracter
    })
  })
    .then(res => res.json())
    .then(data => {

      actualizarTabla(data);
      btn.innerText = originalText;
      btn.disabled = false;
      const modal = bootstrap.Modal.getInstance(document.getElementById('modalFiltro'));
      modal.hide();
    })
    ;
}
function mostrarCarga() {
  document.getElementById("carga").style.display = "flex";
}

function ocultarCarga() {
  document.getElementById("carga").style.display = "none";
}