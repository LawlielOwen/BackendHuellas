document.addEventListener("DOMContentLoaded", function () {
    const usuarioData = localStorage.getItem("usuario");
    const animalData = localStorage.getItem("animalSeleccionado");

    if (usuarioData && animalData) {
        const usuario = JSON.parse(usuarioData);
        const animal = JSON.parse(animalData);

        const nombreInput = document.getElementById("nombre");
        const animalInput = document.getElementById("animal");
       

        if (nombreInput && animalInput ) {
            nombreInput.value = `${usuario.nombre} ${usuario.app} ${usuario.apm}`;
            
            animalInput.value = animal.nombre;
        } 
        window.idAnimal = animal.id;
        window.idAdoptante = usuario.idAdoptante;

    }
    
});



function enviar(){
const telefono = document.getElementById("telefono").value;
const correo = document.getElementById("correo").value;
const direccion = document.getElementById("direccion").value;
const motivo = document.getElementById("motivo").value;
if(!telefono || !correo || !direccion || !motivo) {
    const alerta = document.getElementById("fallo");
    document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
    alerta.classList.remove("d-none");
    return;
}
const boton = document.getElementById("boton");
const textoOriginal = boton.innerHTML;
boton.innerHTML = `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Enviando...`;
boton.disabled = true;
const nuevaSoli = {
    motivo: motivo,
    telefono: telefono,
    correo: correo,
    direccion: direccion,
    idAnimal: parseInt(window.idAnimal),
    idAdoptante: parseInt(window.idAdoptante),
}
fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/saveSolicitud", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nuevaSoli)
        })
            .then(res => res.json())
            .then(data => {
            boton.innerHTML = textoOriginal;
            boton.disabled = false;
            
             mostrarAlertaExito();
            })
    };
function cerrarAlerta() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}
function mostrarAlertaExito() {
    Swal.fire({
        title: '¡Solicitud enviada!',
        text: 'Gracias por tu interés en adoptar. Nos pondremos en contacto contigo pronto.',
        icon: 'success',
        confirmButtonText: 'Volver a inicio',
        background: '#fffaf3', 
        color: '#4b3f2f',       
        iconColor: '#CBBFA3',   
        customClass: {
            confirmButton: 'boton-confirmacion-adopcion',
            popup: 'popup-adopcion'
        }
    }).then(() => {
        window.location.href = "inicioAdopciones.html";
    });
}
document.getElementById("formAdopcion").addEventListener("submit", function(event) {
    event.preventDefault(); 
    enviar();
});