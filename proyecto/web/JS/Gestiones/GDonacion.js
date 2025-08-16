let donaciones = [];
let count = [];
let montoDonacion=[];
document.addEventListener("DOMContentLoaded", function () {
    cargarDonaciones();
    cargarCount();
    CargarTotal();
});

function cargarDonaciones() {
    fetch("http://localhost:8080/ProyectoHuellas/api/donacion/getDonaciones")
        .then(res => res.json())
        .then(data => {
            donaciones = data;
            let filas = "";

            data.forEach(d => {
                filas += `
                  <tr>
            <td>${d.nombreDonante}</td>
            <td>$${d.montoDonacion}</td>
            <td>${d.fechaDonacion}</td>
            <td>${d.centroDonacion}</td>
            
            
        </tr>
                `;
            });

            document.getElementById("cuerpoTabla").innerHTML = filas;
        })
        .catch(error => {
            console.error("Error cargando las donaciones:", error);
            alert("Error al cargar las donaciones.");
        });
}

function cargarCount() {
    fetch("http://localhost:8080/ProyectoHuellas/api/donacion/getContDonaciones")
        .then(res => res.json())
        .then(data => {
            let conteoDonaciones = 0;

            if (data && data.length > 0) {
                cantidadDonaciones = data[0].conteoDonaciones || data[0].donaciones || 0;
            }

            document.getElementById("contadorDonaciones").textContent = cantidadDonaciones;
        })
        .catch(error => {
            console.error("no cargan las donaciones mi pa :", error);
        });
}

function CargarTotal() {
    fetch("http://localhost:8080/ProyectoHuellas/api/donacion/getTotal")         
    .then(res => res.json())
    .then(data => {
        let totalDonaciones = 0; // Declarar aquí la variable
        
        if (data && data.length > 0) {
            // Ajusta el nombre según la propiedad de Java que esté en JSON
            totalDonaciones = data[0].totalDonacion || data[0].montoDonacion|| 0;
        }

        document.getElementById("totalDonaciones").textContent = "$ " + totalDonaciones; 
    })
    .catch(error => {
        console.error("no cargan las donaciones mi pa :", error);
    });
}

