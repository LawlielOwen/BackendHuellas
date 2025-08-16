const urlBase = "http://localhost:8080/ProyectoHuellas/api/Afiliacion"; // Cambia según tu proyecto
let afiliadosMemoria = []; // Guarda la lista actual para rellenar el modal de editar

// --------- GETALL ---------
document.addEventListener('DOMContentLoaded', function () {
    getAllAfiliados();
});


function getAllAfiliados() {
    fetch(urlBase + "/getAll")
    .then(r => r.json())
    .then(lista => {
        afiliadosMemoria = lista;
        let tbody = document.querySelector('.tablaGestiones tbody');
        tbody.innerHTML = "";
        if (!Array.isArray(lista) || lista.length === 0) {
            tbody.innerHTML = `<tr><td colspan="7">Sin registros</td></tr>`;
            return;
        }
        lista.forEach(a => {
            tbody.innerHTML += `
                <tr>
                  <td class="mascotasCol">${a.nombre}</td>
                  <td>${a.correo}</td>
                  <td>${a.telefono}</td>
                  <td>${a.tipoAfiliado}</td>
                  <td>${a.disponibilidadAfiliado}</td>
                  <td>${a.medioContactoAfiliado}</td>
                  <td>
                        <button class="btn btn-sm btn-outline-secondary me-1"
                                onclick="abrirModalModificar(${a.idAfiliado})"
                                data-bs-toggle="modal" data-bs-target="#modAfiliado">
                            <i class='bx bxs-edit'></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger"
                                onclick="abrirModalEliminar(${a.idAfiliado},'${a.nombrePersona}')"
                                data-bs-toggle="modal" data-bs-target="#eliminarAfiliado">
                            <i class='bx bx-trash'></i>
                        </button>
                  </td>
                </tr>
            `;
        });
    })
    .catch(() => {
        let tbody = document.querySelector('.tablaGestiones tbody');
        tbody.innerHTML = `<tr><td colspan="7">Error al cargar datos</td></tr>`;
    });
}

// --------- ELIMINAR ---------
function abrirModalEliminar(id, nombre) {
    document.getElementById('idAfiliado').value = id;
    document.getElementById('nombreAfiliadoEliminar').textContent = nombre;
}
document.getElementById("formEliminarAfiliado").onsubmit = function(e){
    e.preventDefault();
    eliminarAfiliado(document.getElementById('idAfiliado').value);
};
function eliminarAfiliado(idAfiliado) {
    fetch(urlBase + "/eliminar", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "idAfiliado=" + idAfiliado
    })
    .then(r => r.json())
    .then(data => {
        alert(data.result || data.status || data.error);
        getAllAfiliados();
        var modal = bootstrap.Modal.getOrCreateInstance(document.getElementById('eliminarAfiliado'));
        modal.hide();
    });
}

// --------- MODIFICAR ---------
function abrirModalModificar(idAfiliado) {
    // Busca en la memoria y llena el modal
    const a = afiliadosMemoria.find(x => x.idAfiliado == idAfiliado);
    if (!a) return;
    document.getElementById('idAfiliadoMod').value = a.idAfiliado;
    // Asegúrate de tener este campo oculto en el form:
    if(document.getElementById('idPersonaMod')) document.getElementById('idPersonaMod').value = a.idPersona;
    document.getElementById('nombreMod').value = a.nombre;
    document.getElementById('correoMod').value = a.correo;
    document.getElementById('telefonoMod').value = a.telefono;
    document.getElementById('mascotasMod').value = a.mascotasAfiliado;
    document.getElementById('tipoAfiliadoMod').value = a.tipoAfiliado;
    document.getElementById('frecuenciaMod').value = a.frecuenciaAfiliado;
    document.getElementById('disponibilidadMod').value = a.disponibilidadAfiliado;
    document.getElementById('medioContactoMod').value = a.medioContactoAfiliado;
}
document.getElementById("formModificarAfiliado").onsubmit = function(e){
    e.preventDefault();
    modificarAfiliado();
};
function modificarAfiliado() {
    const afiliado = {
        idPersona: document.getElementById('idPersonaMod') ? document.getElementById('idPersonaMod').value : "", // Opcional
        nombre: document.getElementById('nombreMod').value,
        correo: document.getElementById('correoMod').value,
        telefono: document.getElementById('telefonoMod').value,
        idAfiliado: document.getElementById('idAfiliadoMod').value,
        mascotasAfiliado: document.getElementById('mascotasMod').value,
        tipoAfiliado: document.getElementById('tipoAfiliadoMod').value,
        frecuenciaAfiliado: document.getElementById('frecuenciaMod').value,
        disponibilidadAfiliado: document.getElementById('disponibilidadMod').value,
        deseoContactoAfiliado: "Sí", // Modifica si usas este campo
        medioContactoAfiliado: document.getElementById('medioContactoMod').value,
        comentariosAfiliado: "", // Modifica si usas este campo
        idCentro: 1 // Cambia si tienes este campo en el modal
    };

    fetch(urlBase + "/modificar", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "datos=" + encodeURIComponent(JSON.stringify(afiliado))
    })
    .then(r => r.json())
    .then(data => {
        alert(data.status || data.result || data.error);
        getAllAfiliados();
        var modal = bootstrap.Modal.getOrCreateInstance(document.getElementById('modAfiliado'));
        modal.hide();
    });
}

// --------- BUSCAR ---------
// Escucha cuando el usuario escribe en el input de búsqueda
document.getElementById('buscar').addEventListener('input', function () {
    let texto = this.value.trim();
    if (texto === "") {
        getAllAfiliados(); // Si está vacío, muestra todo
        return;
    }
    fetch(urlBase + "/buscar", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "texto=" + encodeURIComponent(texto)
    })
    .then(r => r.json())
    .then(lista => {
        let tbody = document.querySelector('.tablaGestiones tbody');
        tbody.innerHTML = "";
        if (!Array.isArray(lista) || lista.length === 0) {
            tbody.innerHTML = `<tr><td colspan="7">Sin registros</td></tr>`;
            return;
        }
        lista.forEach(a => {
            tbody.innerHTML += `
                <tr>
                  <td>${a.nombre}</td>
                  <td>${a.correo}</td>
                  <td>${a.telefono}</td>
                  <td>${a.tipoAfiliado}</td>
                  <td>${a.disponibilidadAfiliado}</td>
                  <td>${a.medioContactoAfiliado}</td>
                  <td>
                      <button class="btn btn-sm btn-outline-secondary me-1" onclick="abrirModalModificar(${a.idAfiliado})" data-bs-toggle="modal" data-bs-target="#modAfiliado"><i class='bx bxs-edit'></i></button>
                      <button class="btn btn-sm btn-outline-danger" onclick="abrirModalEliminar(${a.idAfiliado},'${a.nombrePersona}')" data-bs-toggle="modal" data-bs-target="#eliminarAfiliado"><i class='bx bx-trash'></i></button>
                  </td>
                </tr>
            `;
        });
    })
    .catch(() => {
        let tbody = document.querySelector('.tablaGestiones tbody');
        tbody.innerHTML = `<tr><td colspan="7">Error al buscar</td></tr>`;
    });
});
