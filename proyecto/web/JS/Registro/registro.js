function Registro() {
    const nombre = document.getElementById("nombre").value;
    const app = document.getElementById("app").value;
    const apm = document.getElementById("apm").value;
    const correo = document.getElementById("correo").value;
    const telefono = document.getElementById("telefono").value;
    const contra = document.getElementById("contra").value;
    const genero = document.getElementById("genero").value;
    const fechaNacimiento = document.getElementById("fechaNacimiento").value;
    const alerta = document.getElementById("fallo");
    if (nombre === "" || app === "" || apm === "" || correo === "" || telefono === "" || contra === "" || genero === "" || fechaNacimiento === "") {
        document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
        return;
    }
   const seis = /.{6,}/.test(contra);
    if (seis === false) {
        document.getElementById("mensaje-error").innerHTML= "La contraseña debe contener al menos 6 caracteres.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
        return;
    }

     const registro = {
        nombre: nombre,
        app:app,
        apm:apm,
        fechaNacimiento:fechaNacimiento,
        correo:correo,
        contraseña:contra,
        telefono:telefono,
        genero:genero
        
    };

    fetch("http://localhost:8080/ProyectoHuellas/api/adoptante/registroAd", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body:JSON.stringify(registro)
       
    })
    .then(res => res.json())
    .then(() => {
            window.location.href = "Login.html";
    });
}
function cerrarAlerta() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}
