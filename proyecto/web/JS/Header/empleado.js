
function cargarUsuarioNav() {
    const usuarioData = localStorage.getItem("usuario");

    if (usuarioData) {
        const usuario = JSON.parse(usuarioData);
        
        // Fuerza la imagen por defecto si la actual no es válida
        let fotoPerfil = usuario.foto;
        if (!fotoPerfil || !fotoPerfil.startsWith('data:image')) {
            fotoPerfil = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=';
        }

        const nombreCompleto = `${usuario.nombre} ${usuario.app}`;

        document.getElementById("usuarioNav").innerHTML = `
            <img src="${fotoPerfil}" class="fotoPerfil" width="32" height="32"
                 style="border-radius: 50%; object-fit: cover;">
            <li class="dropdown despliegueLogin">
                <button class="btn dropdown-toggle botonUsuario" data-bs-toggle="dropdown"
                    aria-expanded="false">
                    ${nombreCompleto}
                </button>
                <ul class="dropdown-menu dropdown-menu-light" style="left: -100px !important; min-width: 280px;">
                    <li>
                        <div class="dropdown-item perfil p-3 bg-white">
                            <div class="perfil-teams d-flex align-items-center">
                                <img src="${fotoPerfil}" width="64" height="64"
                                     style="border-radius: 50%; object-fit: cover; margin-right:10px;">
                                <div style="margin-left: -5px;">
                                    <div class="fw-semibold text-dark">${nombreCompleto}</div>
                                    <div class="text-muted small">${usuario.correo}</div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li><hr class="dropdown-divider my-1"></li>
                    <li>
                        <a class="dropdown-item py-2 px-3 text-dark d-flex align-items-center gap-2" 
                           href="#" onclick="cerrarSesion()">
                            <i class='bx bx-log-out fs-5'></i>
                            <span class="d-none d-sm-inline">Cerrar sesión</span>
                        </a>
                    </li>
                </ul>
            </li>
        `;
    }
}


function cerrarSesion() {
    localStorage.removeItem("usuario");
    window.location.href = "../Empleados/loginEmpleados.html";
}

// Ejecutar al cargar la página
document.addEventListener("DOMContentLoaded", cargarUsuarioNav);

