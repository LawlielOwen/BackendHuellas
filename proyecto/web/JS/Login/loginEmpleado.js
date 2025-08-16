

function LogIn() {
    const correo = document.getElementById("correo").value;
    const contra = document.getElementById("password").value;
    const alerta = document.getElementById("fallo");

    if (correo === "" || contra === "") {
        document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
        return;
    }

    const datos = {
        correo: correo,
        contraseña: contra
    };

    fetch('/api/empleadosLogin/validar', {
        method: 'POST',
        headers: {
             "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(datos)
    })
    .then(response => {
        if (!response.ok) throw new Error("Error en la solicitud");
        return response.json();
    })
    .then(data => {
        if (data.mensaje) {
            document.getElementById("mensaje-error").innerHTML = data.mensaje;
            alerta.classList.remove("d-none");
            alerta.classList.add("show");
        } else {
            localStorage.setItem("usuario", JSON.stringify({
                idEmpleado: data.idEmpleado,
                nombre: data.nombre,
                app: data.app,
                apm: data.apm,
                correo: data.correo,
                foto: data.foto || '../img/perros/user-circle-solid-24.png'
            }));

            document.getElementById('correo').value = "";
            document.getElementById('password').value = "";
            window.location.href = "/ProyectoHuellas/Empleados/GestionMascotas.html";
        }
    })
    .catch(error => {
        console.error("Error:", error);
        document.getElementById("mensaje-error").innerHTML = "Error al procesar la solicitud.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
    });
}

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("formularioLogin").addEventListener("submit", function(e) {
        e.preventDefault();
        LogIn();
    });
});

