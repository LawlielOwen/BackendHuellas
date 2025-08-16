cliente = [];
buscarid = [];

document.addEventListener("DOMContentLoaded", function () {
    cargarAdoptante();
     sesionIniciada();
});

function LogIn(){
   const correo = document.getElementById("correo").value;
   const contra = document.getElementById("pass").value;
   const alerta = document.getElementById("fallo");
   if (correo === "" || contra === "") {
       document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
       alerta.classList.remove("d-none");
       alerta.classList.add("show");
       return;
   }

   const cuenta = {
      correo:correo,
        contraseña:contra,
   };
     fetch("http://localhost:8080/ProyectoHuellas/api/adoptante/consultaAd", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body:JSON.stringify(cuenta)
       
    })
    .then(res => res.json())
     .then (response => {    
           if (response.mensaje) {
        document.getElementById("mensaje-error").innerHTML = response.mensaje;
       alerta.classList.remove("d-none");
            alerta.classList.add("show"); 
    } else {
          localStorage.setItem("usuario", JSON.stringify({
                idAdoptante: response.idAdoptante,
                nombre: response.nombre,
                app: response.app,
                apm : response.apm,
                correo: response.correo
            }));
        window.location.href = "inicioAdopciones.html";
    }})
}
document.addEventListener("DOMContentLoaded", function () {
    const formLogin = document.getElementById("formularioLogin");

    if (formLogin) {
        formLogin.addEventListener("submit", function (e) {
            e.preventDefault();
            LogIn(); 
        });
    }
});

function cerrarAlerta() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}

