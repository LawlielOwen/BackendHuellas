adoptantes = [];
let idAdopEliminar = null;
document.addEventListener("DOMContentLoaded", function () {
    cargarAdoptantes();
    cargarContadores();
});
function cargarAdoptantes() {
    fetch("http://localhost:8080/ProyectoHuellas/api/adoptante/getAll")
        .then(res => res.json())
        .then(data => {
            adoptantes = data;
            let filas = "";

            data.forEach(a => {
                filas += `
                    <tr>
                        <td class="mascotasCol">${a.nombre} ${a.app} ${a.apm}</td>
                       <td>${a.fechaNacimiento}</td>
                        <td>${a.genero}</td>
                        <td>${a.correo}</td>
                        <td>${a.telefono}</td>
                        <td>
                             <button class="btn btn-sm btn-outline-secondary me-1" data-bs-toggle="modal"
                                    data-bs-target="#modAdoptante"  onclick="cargarInfo(${a.idAdoptante})">
                                    <i class='bx bxs-edit'></i>
                                </button>
                                <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal"
                                    data-bs-target="#eliminarAdoptante"  onclick="prepararEliminar(${a.idAdoptante})"><i class='bx bx-trash'></i></button>
                        </td>
                    </tr>
                `;
            });

            document.getElementById("cuerpoTabla").innerHTML = filas;
        });
}
function cargarContadores() {

    fetch("http://localhost:8080/ProyectoHuellas/api/adoptante/contarDisponibles")
        .then(res => res.json())
        .then(num => {
            document.getElementById("contadorRegistrados").innerHTML = num;
        })
}
function actualizarTabla(data) {
    let filas = "";
    data.forEach(a => {
        filas += `
                    <tr>
                        <td class="mascotasCol">${a.nombre} ${a.app} ${a.apm}</td>
                       <td>${a.fechaNacimiento}</td>
                        <td>${a.genero}</td>
                        <td>${a.correo}</td>
                        <td>${a.telefono}</td>
                        <td>
                             <button class="btn btn-sm btn-outline-secondary me-1" data-bs-toggle="modal"
                                    data-bs-target="#modAdoptante"  onclick="cargarInfo(${a.idAdoptante})">
                                    <i class='bx bxs-edit'></i>
                                </button>
                                <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal"
                                    data-bs-target="#eliminarAdoptante"  onclick="prepararEliminar(${a.idAdoptante})"><i class='bx bx-trash'></i></button>
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
            cargarAdoptantes();
        } else {
            buscarAdoptante(termino);
        }
    }
});
function buscarAdoptante(nombre) {
    fetch("http://localhost:8080/ProyectoHuellas/api/adoptante/buscarA", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ nombre: nombre })
    })
        .then(res => res.json())
        .then(data => {
            actualizarTabla(data);
        })
        ;
}
function cargarInfo(id) {
    const adoptante = adoptantes.find(a => a.idAdoptante === id);
    if (adoptante) {
        document.getElementById("idAdoptanteMod").value = adoptante.idAdoptante;
        document.getElementById("nombreMod").value = adoptante.nombre;
        document.getElementById("appMod").value = adoptante.app;
        document.getElementById("apmMod").value = adoptante.apm;
        document.getElementById("fechaNacimientoMod").value = adoptante.fechaNacimiento;
        document.getElementById("generoMod").value = adoptante.genero;
        document.getElementById("correoMod").value = adoptante.correo;
        document.getElementById("contraMod").value = adoptante.contraseña;
        document.getElementById("telefonoMod").value = adoptante.telefono;
    }
}
function modificarAdoptante() {
  const  adoptante = {
        idAdoptante: document.getElementById("idAdoptanteMod").value,
        nombre: document.getElementById("nombreMod").value,
        app: document.getElementById("appMod").value,
        apm: document.getElementById("apmMod").value,
        fechaNacimiento: document.getElementById("fechaNacimientoMod").value,
        genero: document.getElementById("generoMod").value,
        correo: document.getElementById("correoMod").value,
        contraseña: document.getElementById("contraMod").value,
        telefono: document.getElementById("telefonoMod").value
    }
if(!adoptante.nombre || !adoptante.app || !adoptante.apm || !adoptante.fechaNacimiento || !adoptante.genero || !adoptante.correo || !adoptante.contraseña || !adoptante.telefono) {
        const alerta = document.getElementById("fallo");
        document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
        alerta.classList.remove("d-none");
        return
}
    fetch("http://localhost:8080/ProyectoHuellas/api/adoptante/modAd", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(
           adoptante
        )
    })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                cargarAdoptantes();
                cargarContadores();
               
            } else {
                alert("Error al modificar el adoptante");
            }
        });
}
function prepararEliminar(id) {
    idAdopEliminar = id;
    const adoptante = adoptantes.find(a => a.idAdoptante === id);
    if (adoptante) {
        document.getElementById("nombreAdoptanteEliminar").innerText = `${adoptante.nombre} ${adoptante.app} ${adoptante.apm}`;
    }
}
function eliminarAdoptante() {

    const adop = {
        idAdoptante: idAdopEliminar
    };
fetch("http://localhost:8080/ProyectoHuellas/api/adoptante/deleteAdop", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(adop)
    })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                cargarAdoptantes();
                cargarContadores();
                idAdopEliminar = null;
            } else {
                alert("Error al eliminar el adoptante");
            }
        });
}
function cerrarAlerta() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}

document.getElementById('modAdoptante').addEventListener('hidden.bs.modal', function () {
    cerrarAlerta(); 
});