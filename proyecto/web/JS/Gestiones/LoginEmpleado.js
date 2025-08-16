function LogIn() {
    const correo = document.getElementById("email").value;
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
    fetch('http://localhost:8080/ProyectoHuellas/api/Empleados/consultaEmpleado', {
        method: 'POST',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            datosUsuario: JSON.stringify(datos)

        })
    }).then(response => response.json())
        .then(response => {
            if (response.mensaje) {
                document.getElementById("mensaje-error").innerHTML = response.mensaje;
                alerta.classList.remove("d-none");
                alerta.classList.add("show");
            } else {
                localStorage.setItem("email", correo);

                document.getElementById('email').value = " ";
                document.getElementById('password').value = " ";
                window.location.href = "GestionMascotas.html";
            }
        })
}
function cerrarAlerta() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}
document.getElementById("formularioLogin").addEventListener("submit", function (event) {
    event.preventDefault();
    LogIn();
});
