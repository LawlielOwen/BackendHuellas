
// AfiliacionAgregar.js

document.addEventListener("DOMContentLoaded", function() {
    const url = "http://localhost:8080/ProyectoHuellas/api/Afiliacion/agregar";
    const form = document.getElementById("formAfiliacion");

    form.addEventListener("submit", function(e) {
        e.preventDefault();

        // Obtenemos los datos del formulario
        const afiliado = {
            nombre: document.getElementById("nombre").value,
            correo: document.getElementById("correo").value,
            telefono: document.getElementById("telefono").value,
            mascotasAfiliado: document.getElementById("mascotas").value,
            tipoAfiliado: document.getElementById("tipoApoyo").value,
            frecuenciaAfiliado: document.getElementById("frecuenciaAyuda").value,
            disponibilidadAfiliado: document.getElementById("disponibilidad").value,
            deseoContactoAfiliado: document.getElementById("contactar").checked ? "Sí" : "No",
            medioContactoAfiliado: document.getElementById("medioContacto").value,
            comentariosAfiliado: document.getElementById("mensaje").value,
            idCentro: 1 // Cambia esto según tu app
        };

        fetch(url, {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded"},
            body: "datos=" + encodeURIComponent(JSON.stringify(afiliado))
        }).then(r => r.json())
        .then(data => {
            if(data.status === "ok") {
                alert("¡Afiliado agregado!");
                form.reset();
            } else {
                alert("Error: " + (data.error || "No se pudo agregar"));
            }
        });
    });
});
