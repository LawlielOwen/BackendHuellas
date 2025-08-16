document.addEventListener("DOMContentLoaded", function () {
    fetch("/api/centros/countActivos")
        .then(response => response.json())
        .then(data => {
            if (data.total_activos !== undefined) {
                document.getElementById("contadorAdoptados").textContent = data.total_activos;
            } else {
                document.getElementById("contadorAdoptados").textContent = "0";
            }
        })
        .catch(err => {
            console.error("Error obteniendo activos:", err);
            document.getElementById("contadorAdoptados").textContent = "0";
        });

    fetch("/api/centros/countInactivos")
        .then(response => response.json())
        .then(data => {
            if (data.total_inactivos !== undefined) {
                document.getElementById("contadorDisponibles").textContent = data.total_inactivos;
            } else {
                document.getElementById("contadorDisponibles").textContent = "0";
            }
        })
        .catch(err => {
            console.error("Error obteniendo inactivos:", err);
            document.getElementById("contadorDisponibles").textContent = "0";
        });
});

const BASE_URL = "/api/centros";
let centros = [];
let idCentroEliminar = null;

// Cargar centros al iniciar
document.addEventListener("DOMContentLoaded", function() {
    cargarCentros();
    
    // Configurar eventos
    document.getElementById("formAgregarCentro").addEventListener("submit", agregarCentro);
    document.getElementById("formModificarCentro").addEventListener("submit", modificarCentro);
    document.getElementById("formEliminarCentro").addEventListener("submit", eliminarCentro);
    document.getElementById("buscarCentro").addEventListener("keyup", buscarCentros);
    
    // Filtros
    document.getElementById("todosCentros").addEventListener("click", () => cargarCentros());
    document.getElementById("activosCentros").addEventListener("click", () => filtrarCentros(1));
    document.getElementById("inactivosCentros").addEventListener("click", () => filtrarCentros(0));
});

// Cargar todos los centros
async function cargarCentros() {
    try {
        const res = await fetch(`${BASE_URL}/getAll`);
        centros = await res.json();
        mostrarCentros(centros);
    } catch (error) {
        console.error("Error cargando centros:", error);
        alert("Error al cargar los centros");
    }
}

// Mostrar centros en la tabla
function mostrarCentros(listaCentros) {
    const tbody = document.getElementById("tbodyCentros");
    tbody.innerHTML = "";

    listaCentros.forEach(centro => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td class="mascotasCol">${centro.nombreCentro}</td>
            <td>
                <span class="${centro.estatus === 1 ? "activado bg-success" : "inactivo bg-danger"}"></span>
                ${centro.estatus === 1 ? "Activo" : "Inactivo"}
            </td>
            <td>${centro.direccionCentro}</td>
            <td>${centro.telefonoCentro}</td>
            <td>${centro.correoCentro}</td>
            <td>${centro.horarioAperturaCentro} - ${centro.horarioCierreCentro}</td>
            <td>
                <button class="btn btn-sm btn-outline-secondary me-1" data-bs-toggle="modal" 
                    data-bs-target="#modificarCentro" onclick='cargarCentroParaEditar(${JSON.stringify(centro)})'>
                    <i class='bx bxs-edit'></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" 
                    data-bs-target="#eliminarCentro" onclick='prepararEliminarCentro(${JSON.stringify(centro)})'>
                    <i class='bx bx-trash'></i>
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Filtrar centros por estatus
function filtrarCentros(estatus) {
    const centrosFiltrados = centros.filter(c => c.estatus === estatus);
    mostrarCentros(centrosFiltrados);
}

// Buscar centros por nombre
function buscarCentros() {
    const termino = this.value.toLowerCase();
    if (termino === "") {
        mostrarCentros(centros);
    } else {
        const resultados = centros.filter(c => 
            c.nombreCentro.toLowerCase().includes(termino)
        );
        mostrarCentros(resultados);
    }
}

// Cargar datos en modal de edición
function cargarCentroParaEditar(centro) {
    document.getElementById("idCentroMod").value = centro.idCentro;
    document.getElementById("nombreCentroMod").value = centro.nombreCentro;
    document.getElementById("direccionCentroMod").value = centro.direccionCentro;
    document.getElementById("telefonoCentroMod").value = centro.telefonoCentro;
    document.getElementById("correoCentroMod").value = centro.correoCentro;
    document.getElementById("horaAperturaCentroMod").value = centro.horarioAperturaCentro;
    document.getElementById("horaCierreCentroMod").value = centro.horarioCierreCentro;
}

// Preparar eliminación de centro
function prepararEliminarCentro(centro) {
    idCentroEliminar = centro.idCentro;
    document.getElementById("idCentroEliminar").value = centro.idCentro;
    document.getElementById("nombreCentroEliminar").textContent = centro.nombreCentro;
}

// Agregar nuevo centro
async function agregarCentro(e) {
    e.preventDefault();
    
    const nuevoCentro = {
        nombreCentro: document.getElementById("nombreCentro").value.trim(),
        direccionCentro: document.getElementById("direccionCentro").value.trim(),
        telefonoCentro: document.getElementById("telefonoCentro").value.trim(),
        correoCentro: document.getElementById("correoCentro").value.trim(),
        horarioAperturaCentro: document.getElementById("horaAperturaCentro").value,
        horarioCierreCentro: document.getElementById("horaCierreCentro").value,
        estatus: 1
    };

    try {
        const res = await fetch(`${BASE_URL}/insert`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(nuevoCentro)
        });
        
        const data = await res.json();
        alert(data.result || data.error);
        
        if (data.result) {
            cargarCentros();
            const modal = bootstrap.Modal.getInstance(document.getElementById("agregarCentro"));
            modal.hide();
            e.target.reset();
        }
    } catch (error) {
        console.error("Error agregando centro:", error);
        alert("Error al agregar centro");
    }
}

// Modificar centro existente
async function modificarCentro(e) {
    e.preventDefault();
    
    const centroMod = {
        idCentro: parseInt(document.getElementById("idCentroMod").value),
        nombreCentro: document.getElementById("nombreCentroMod").value.trim(),
        direccionCentro: document.getElementById("direccionCentroMod").value.trim(),
        telefonoCentro: document.getElementById("telefonoCentroMod").value.trim(),
        correoCentro: document.getElementById("correoCentroMod").value.trim(),
        horarioAperturaCentro: document.getElementById("horaAperturaCentroMod").value,
        horarioCierreCentro: document.getElementById("horaCierreCentroMod").value,
        estatus: 1
    };

    try {
        const res = await fetch(`${BASE_URL}/update`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(centroMod)
        });
        
        const data = await res.json();
        alert(data.result || data.error);
        
        if (data.result) {
            cargarCentros();
            const modal = bootstrap.Modal.getInstance(document.getElementById("modificarCentro"));
            modal.hide();
        }
    } catch (error) {
        console.error("Error modificando centro:", error);
        alert("Error al modificar centro");
    }
}

// Eliminar centro (borrado lógico)
async function eliminarCentro(e) {
    e.preventDefault();
    
    try {
        const res = await fetch(`${BASE_URL}/delete`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ idCentro: idCentroEliminar })
        });
        
        const data = await res.json();
        alert(data.result || data.error);
        
        if (data.result) {
            cargarCentros();
            const modal = bootstrap.Modal.getInstance(document.getElementById("eliminarCentro"));
            modal.hide();
        }
    } catch (error) {
        console.error("Error eliminando centro:", error);
        alert("Error al eliminar centro");
    }
}

// Inicializar la tabla al cargar la página
window.addEventListener("load", cargarCentros);