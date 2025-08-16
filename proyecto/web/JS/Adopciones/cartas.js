animales = [];
let centros = [];

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
  fetch("http://localhost:8080/ProyectoHuellas/api/inicio/getAll")
    .then(res => res.json())
    .then(data => {
      animales = data;

      const ultimosAnimales = data.slice(0, 4);

      let cards = "";

      ultimosAnimales.forEach(a => {
        cards += `
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
  </div>
`;

      });
      ocultarCarga();
      document.getElementById("cartas").innerHTML = cards;

    });
}
function mostrarInfo(id) {
  const animal = animales.find(a => a.idAnimal === id);
  if (!animal) return;
  const centro = centros.find(c => c.idCentro === animal.idCentro);
  const informacion = `
     <div class="modal-dialog modal-dialog-centered modal-lg m-0">
  <div class="modal-content" style="border: none;">
   <div class="modal-header text-white headerModal" style=" border-bottom: none; padding: 1rem;" >
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
function mostrarCarga() {
  document.getElementById("carga").style.display = "flex";
}

function ocultarCarga() {
  document.getElementById("carga").style.display = "none";
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
  fetch("http://localhost:8080/ProyectoHuellas/api/inicio/buscarAnimal", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ nombreAnimal: nombre })
  })
    .then(res => res.json())
    .then(data => {
      document.getElementById("contenedorSecciones").classList.add("d-none");
      document.getElementById("rescatesRecientes").classList.add("d-none");
      document.getElementById("busquedaResultados").classList.remove("d-none");
      actualizarTabla(data);
    })
    ;
}
function actualizarTabla(animalesFiltrados) {
  const contenedor = document.getElementById("contenedorCartasBusqueda");
  contenedor.innerHTML = "";

  if (animalesFiltrados.length === 0) {
    contenedor.innerHTML = `<p class="text-center w-100">No se encontraron resultados.</p>`;
    return;
  }

  animalesFiltrados.forEach(a => {
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
}
function aplicarFiltros() {
  let especiesSeleccionadas = [];
  document.querySelectorAll('.filtroEspecie:checked').forEach(cb => {
    especiesSeleccionadas.push(cb.value);
  });


  const especie = especiesSeleccionadas.length > 0 ? especiesSeleccionadas[0] : "";

  const edad = document.getElementById("filtroEdad").value;
  const genero = document.getElementById("filtroSexo").value;
  const caracter = document.getElementById("filtroCaracter").value;

  let data = {
    especie: especie,
    edad: edad,
    genero: genero,
    caracter: caracter
  };


  fetch("http://localhost:8080/ProyectoHuellas/api/inicio/filtroTodos", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  })
    .then(res => res.json())
    .then(animales => {
      actualizarTabla(animales);

    })
    .catch(err => console.error(err));
}

function aplicarFiltrosResponsive() {
  const btn = document.getElementById("btnAplicarFiltros");
  const originalText = btn.innerText;
  btn.innerText = "Filtrando...";
  btn.disabled = true;
  const edad = document.getElementById("filtroEdadModal").value;
  const genero = document.getElementById("filtroSexoModal").value;
  const caracter = document.getElementById("filtroCaracterModal").value;

  let especiesSeleccionadas = [];
  document.querySelectorAll('.filtroEspecieModal:checked').forEach(cb => {
    especiesSeleccionadas.push(cb.value);
  });


  const especie = especiesSeleccionadas.length > 0 ? especiesSeleccionadas[0] : "";


  let data = {
    especie: especie,
    edad: edad,
    genero: genero,
    caracter: caracter
  };


  fetch("http://localhost:8080/ProyectoHuellas/api/inicio/filtroTodos", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  })
    .then(res => res.json())
    .then(animales => {
      actualizarTabla(animales);
      btn.innerText = originalText;
      btn.disabled = false;
      const modal = bootstrap.Modal.getInstance(document.getElementById('modalFiltro'));
      modal.hide();
    })

}
