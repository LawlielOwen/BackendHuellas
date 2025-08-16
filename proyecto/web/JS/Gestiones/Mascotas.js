animales = [];
buscarid = [];
centros = [];
let paginaActual = 1;
const animalesPorPagina = 5;
let idAnimalEliminar = null;
let especieSeleccionada = "Todos";
let estatusSeleccionado = "Todos";


document.addEventListener("DOMContentLoaded", function () {
    cargarMascotas();
    cargarCentros();
    cargarContadores();
    document.getElementById("todos").addEventListener("click", () => {
        especieSeleccionada = "Todos";
        filtrarTodo(especieSeleccionada, estatusSeleccionado);
    });
    document.getElementById("perros").addEventListener("click", () => {
        especieSeleccionada = "Perros";
        filtrarTodo(especieSeleccionada, estatusSeleccionado);
    });
    document.getElementById("gatos").addEventListener("click", () => {
        especieSeleccionada = "Gatos";
        filtrarTodo(especieSeleccionada, estatusSeleccionado);
    });

    document.getElementById("todosAnimales").addEventListener("click", () => {
        estatusSeleccionado = "Todos";
        filtrarTodo(especieSeleccionada, estatusSeleccionado);
    });
    document.getElementById("adopcion").addEventListener("click", () => {
        estatusSeleccionado = 1;
        filtrarTodo(especieSeleccionada, estatusSeleccionado);
    });
    document.getElementById("adoptado").addEventListener("click", () => {
        estatusSeleccionado = 2;
        filtrarTodo(especieSeleccionada, estatusSeleccionado);
    });


});
function cargarCentros() {
    fetch("/api/centros/getAll")
        .then(res => res.json())
        .then(data => {
            centros = data;
            let opciones = `<option value="">Selecciona una opción</option>`;
            data.forEach(c => {
                opciones += `<option value="${c.idCentro}">${c.nombreCentro}</option>`;
            });

            document.getElementById("centro").innerHTML = opciones;
            document.getElementById("centroMod").innerHTML = opciones;
        });
}


function cargarMascotas() {
    fetch("/api/mascotas/getAll")
        .then(res => res.json())
        .then(data => {
            animales = data;
            paginaActual = 1;
            mostrarAnimalesPorPagina();
            mostrarBotonesPaginacion();
        });
}
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

function actualizarTabla(data) {
    animales = data;
    paginaActual = 1;
    mostrarAnimalesPorPagina();
    mostrarBotonesPaginacion();
    let filas = "";
    data.forEach(a => {
        filas += `
            <tr>
                <td class="mascotasCol"><span><img src="${a.foto}" alt="Foto de mascota">${a.nombreAnimal}</span></td>
                <td> <span class="activado ${a.estatus === 1 ? 'bg-success' : a.estatus === 2 ? 'bg-warning' : ''}"></span> ${a.estatusTexto}</td>
                <td>${a.genero}</td>
                <td>${a.edad}</td>
                <td>${a.codigoAnimal}</td>
                <td>${a.raza}</td>
                <td>${a.peso}</td>
                <td>
                     <button class="btn btn-sm btn-outline-secondary me-1" data-bs-toggle="modal"
                                    data-bs-target="#modAnimal"  onclick="cargarAnimal(${a.idAnimal})">
                                    <i class='bx bxs-edit'></i>
                                </button>
                                <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal"
                                    data-bs-target="#eliminarAnimal"  onclick="prepararEliminar(${a.idAnimal})"><i class='bx bx-trash'></i></button>
                                <button class="btn btn-sm btn-outline-info"  data-bs-toggle="modal" data-bs-target="#infoAnimal"
                                onclick="mostrarInfoAnimal(${a.idAnimal})">
                                    <i class='bx bxs-info-circle' ></i>
                                </button>
                </td>
            </tr>
        `;
    });
    document.getElementById("cuerpoTabla").innerHTML = filas;
}

function filtrarTodo(especie, estatus) {
    const cuerpo = {};

    cuerpo.especie = especie && especie !== "" ? especie : "Todos";


    if (estatus === "Todos" || estatus === "" || estatus === null || estatus === undefined) {
        cuerpo.estatus = 0;
    } else {
        cuerpo.estatus = estatus;
    }


    if (cuerpo.especie === "Todos" && cuerpo.estatus === 0) {
        cargarMascotas();
        return;
    }

    fetch("/api/mascotas/filtroTodos", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cuerpo)
    })
        .then(res => res.json())
        .then(data => {
            actualizarTabla(data);
        })
        .catch(error => {
            console.error("Error en filtrarTodo:", error);
        });
}



function agregarMascota() {
    const nombre = document.getElementById("nombreAnimal").value.trim();
    const genero = document.getElementById("generoAnimal").value.trim();
    const edadValor = document.getElementById("edadAnimal").value.trim();
    const unidadEdad = document.getElementById("unidadEdad").value;


    if (!edadValor || isNaN(edadValor) || edadValor <= 0) {
        document.getElementById("mensaje-error").innerHTML = "Por favor, ingresa una edad válida.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
        return;
    }


    const edadFormateada = `${edadValor} ${unidadEdad}`;
    if (parseInt(edadValor) === 1) {
        edadFormateada = `${edadValor} ${unidadEdad.replace(/s$/, "")}`;
    }
    const peso = document.getElementById("pesoAnimal").value.trim();
    const especie = document.getElementById("especie").value;
    const raza = document.getElementById("razaAnimal").value.trim();
    const tamanio = document.getElementById("tamanioAnimal").value;
    const fotoFile = document.getElementById("fotoAnimal").files[0];
    const centro = document.getElementById("centro").value;
    const descripcion = document.getElementById("descripcionAnimal").value.trim();
    const caracter = document.getElementById("caracter").value;
    const alerta = document.getElementById("fallo");

    if (!nombre || !genero || !edad || !peso || !especie || !fotoFile || !centro || !descripcion || !raza || !tamanio || !caracter) {
        document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
        return;
    }


    const reader = new FileReader();
    reader.onloadend = function () {

        const base64Foto = reader.result;


        const nuevaMascota = {
            nombreAnimal: nombre,
            genero: genero,
            edad: edadFormateada,
            peso: parseFloat(peso),
            especie: especie,
            descripcion: descripcion,
            raza: raza,
            tamano: tamanio,
            foto: base64Foto,
            idCentro: parseInt(centro),
            caracter: caracter
        };


        fetch("/api/mascotas/saveAnimal", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nuevaMascota)
        })
            .then(res => res.json())
            .then(data => {

                cargarMascotas();
                cargarContadores();
            })
    };

    reader.readAsDataURL(fotoFile);
}
function cargarAnimal(id) {
    const animal = animales.find(a => a.idAnimal == id);
    if (animal) {
        document.getElementById("idAnimalMod").value = animal.idAnimal;
        document.getElementById("nombreMod").value = animal.nombreAnimal;
        document.getElementById("generoMod").value = animal.genero;
        const partesEdad = animal.edad.split(" ");
        document.getElementById("edadMod").value = partesEdad[0] || "";
        document.getElementById("unidadEdadMod").value = partesEdad[1] || "";

        document.getElementById("pesoMod").value = animal.peso;
        document.getElementById("especieMod").value = animal.especie;
        document.getElementById("razaMod").value = animal.raza;
        document.getElementById("tamanioMod").value = animal.tamano;
        document.getElementById("descripcionMod").value = animal.descripcion;
        document.getElementById("centroMod").value = animal.idCentro;
        document.getElementById("fotoVistaMod").src = animal.foto;
        document.getElementById("caracterMod").value = animal.caracter;
    }
}


function modificarAnimal() {
    const edadValor = document.getElementById("edadMod").value.trim();
    const unidadEdad = document.getElementById("unidadEdadMod").value;

    if (!edadValor || isNaN(edadValor) || edadValor <= 0) {
        const alerta = document.getElementById("fallo");
        document.getElementById("mensaje-error").innerHTML = "Por favor, ingresa una edad válida.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
        return;
    }

    let edadFormateada = `${edadValor} ${unidadEdad}`;
    if (parseInt(edadValor) === 1) {
        edadFormateada = `${edadValor} ${unidadEdad.replace(/s$/, "")}`; // Quitar "s" en singular
    }

    const animal = {
        idAnimal: parseInt(document.getElementById("idAnimalMod").value),
        nombreAnimal: document.getElementById("nombreMod").value,
        genero: document.getElementById("generoMod").value,
        edad: edadFormateada, 
        peso: parseFloat(document.getElementById("pesoMod").value),
        especie: document.getElementById("especieMod").value,
        raza: document.getElementById("razaMod").value,
        tamano: document.getElementById("tamanioMod").value,
        descripcion: document.getElementById("descripcionMod").value,
        idCentro: parseInt(document.getElementById("centroMod").value),
        caracter: document.getElementById("caracterMod").value
    };

    if (!animal.nombreAnimal || !animal.genero || !animal.edad || !animal.peso ||
        !animal.especie || !animal.raza || !animal.tamano || !animal.descripcion ||
        !animal.idCentro || !animal.caracter) {
        const alerta = document.getElementById("fallo");
        document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
        return;
    }

    fetch("/api/mascotas/updateAnimal", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(animal)
    })
        .then(res => res.json())
        .then(() => {
            cargarMascotas();
            cargarContadores();
        });
}



function prepararEliminar(id) {
    idAnimalEliminar = id;
    const animal = animales.find(a => a.idAnimal == id);
    if (animal) {
        document.getElementById("nombreA").innerText = `${animal.nombreAnimal}`;
    }
}

function eliminarAnimal() {


    const animal = {
        idAnimal: idAnimalEliminar
    };

    fetch("/api/mascotas/deleteAnimal", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(animal)
    })
        .then(res => res.json())
        .then(() => {
            cargarMascotas();
            cargarContadores();
        })

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
    fetch("/api/mascotas/buscarAnimal", {
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
function cargarContadores() {

    fetch("/api/mascotas/contarDisponibles")
        .then(res => res.json())
        .then(num => {
            document.getElementById("contadorDisponibles").innerHTML = num;
        })


    fetch("/api/mascotas/contarAdoptados")
        .then(res => res.json())
        .then(num => {
            document.getElementById("contadorAdoptados").innerHTML = num;
        })

}
function mostrarInfoAnimal(idAnimal) {
    const animal = animales.find(a => a.idAnimal == idAnimal);
    const centro = centros.find(c => c.idCentro == animal.idCentro);


    document.getElementById('infoFotoAnimal').src = animal.foto || 'img/default-pet.jpg';
    document.getElementById('infoNombre').textContent = animal.nombreAnimal || 'Sin nombre';
    document.getElementById('infoCodigo').textContent = animal.codigoAnimal || 'N/A';


    const edadTexto = animal.edad ?
        `${animal.edad} ${animal.edad == 1 ? 'año' : 'años'}` :
        'Edad no especificada';

    document.getElementById('infoEspecieRaza').textContent = `${animal.especie || 'Sin especificar'} / ${animal.raza || 'Mestizo'}`;
    document.getElementById('infoGeneroEdad').textContent = `${animal.genero || 'Desconocido'} / ${edadTexto}`;

    document.getElementById('infoTamanio').textContent = animal.tamano || 'Mediano';
    document.getElementById('infoPeso').textContent = animal.peso ? `${animal.peso} kg` : 'No especificado';
    document.getElementById('infoCentro').textContent = centro?.nombreCentro || 'Centro no asignado';


    const descElement = document.getElementById('infoDescripcion');
    descElement.textContent = animal.descripcion || 'No hay descripción disponible.';
    descElement.style.fontSize = '0.9rem';
    descElement.style.lineHeight = '1.5';


    const estatusBadge = document.getElementById('infoEstatusBadge');


    estatusBadge.className = 'badge py-2 px-3 rounded-pill';

    switch (animal.estatus) {
        case 1:
            estatusBadge.style.backgroundColor = '#5cb85c';
            estatusBadge.innerHTML = '<i class="bx bx-check-circle me-1"></i> Disponible';
            break;
        case 2:
            estatusBadge.style.backgroundColor = '#f0ad4e';
            estatusBadge.innerHTML = '<i class="bx bx-heart me-1"></i> Adoptado';
            break;

    }
}
function cerrarAlerta() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}

document.getElementById('agregarAnimal').addEventListener('hidden.bs.modal', function () {
    cerrarAlerta();
});
document.getElementById('modAnimal').addEventListener('hidden.bs.modal', function () {
    cerrarAlerta();
});