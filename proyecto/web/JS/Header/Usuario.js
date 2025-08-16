

function cargarAdoptante() {
    fetch("/api/adoptante/getAll")
        .then(res => res.json())
        .then(data => {
        cliente = data;
        });
}

function sesionIniciada() {
    const usuarioElemento = document.getElementById("usuario");
    const usuarioData = localStorage.getItem("usuario");
    
    if (usuarioData) {
        const usuario = JSON.parse(usuarioData);
        usuarioElemento.innerHTML = `
        <div class="perfil-container">
            <span class="sesionP">
                <button class="btn btn-outline-light dropdown-toggle d-flex align-items-center gap-2 px-3 py-1 rounded-lg" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class='bx bxs-user-circle fs-5'></i> 
                    <span class="fw-medium d-none d-md-inline">${usuario.nombre}</span>
                </button>
                <ul class="dropdown-menu dropdown-menu-end perfil-dropdown"> 
                    <li>
                        <div class="dropdown-item perfil p-3 bg-white">
                            <div class="perfil-teams d-flex align-items-center">
                                <img src="img/perros/user-circle-solid-24.png" class="foto-perfil-teams me-3" alt="Foto de perfil">
                                <div>
                                    <div class="fw-semibold text-dark">${usuario.nombre} ${usuario.app} ${usuario.apm}</div>
                                    <div class="text-muted small">${usuario.correo}</div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li><hr class="dropdown-divider my-1"></li>
                    <li>
                        <a class="dropdown-item py-2 px-3 text-dark d-flex align-items-center gap-2" href="#" onclick="cerrarSesion()">
                            <i class='bx bx-log-out fs-5'></i>
                            <span class="d-none d-sm-inline">Cerrar sesión</span>
                        </a>
                    </li>
                </ul>
            </span>
        </div>
        `;

        document.getElementById("btnEmpleado").style.display = "none";
    } else {
        usuarioElemento.innerHTML = `
            <a class="nav-link text-light" href="Login.html">Iniciar sesión / Crear cuenta</a>
        `;
        document.getElementById("btnEmpleado").style.display = "flex";
    }
}
function cerrarSesion() {
    localStorage.removeItem("usuario");
    window.location.href = "inicioAdopciones.html";
}

function cerrarAviso() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}
function revisarSesion(idAnimal){
    const alerta = document.getElementById("fallo");
    const usuarioData = localStorage.getItem("usuario");

    if (usuarioData) {
        const animal = animales.find(a => a.idAnimal === idAnimal);
        if (animal) {
           
         localStorage.setItem("animalSeleccionado", JSON.stringify({
    id: animal.idAnimal,
    nombre: animal.nombreAnimal 
}));
        }
     
        window.location.href = "solicitudAdopcion.html";
    } else {
        document.getElementById("mensaje-error").innerText = "Debes iniciar sesión para realizar una adopción.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
    }
}

