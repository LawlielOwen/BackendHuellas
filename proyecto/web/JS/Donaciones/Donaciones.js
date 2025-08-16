// Si marca que sí es donante se despliega el input correo
document.getElementById('esDonante').addEventListener('change', function() {
  const correoDiv = document.getElementById('correoDonanteDiv');
  correoDiv.style.display = this.checked ? 'block' : 'none';
});

// Cargar centros desde API al cargar el DOM
document.addEventListener("DOMContentLoaded", function() {
  cargarCentros();

  // Configurar lógica botones monto
  setupAmountButtons();
});

function cargarCentros() {
  fetch("http://localhost:8080/api/centros/getNombres")
    .then(res => res.json())
    .then(data => {
      const selectInstitucion = document.getElementById("institucion");
      // Limpiar opciones previas (excepto la por defecto)
      selectInstitucion.innerHTML = '<option value="" disabled selected>Selecciona una opción</option>';

      data.forEach(centro => {
        const option = document.createElement("option");
        option.value = centro.idCentro;        // id para el backend
        option.textContent = centro.nombreCentro; // texto que verá el usuario
        selectInstitucion.appendChild(option);
      });
    })
    .catch(err => console.error("Error cargando centros:", err));
}

function setupAmountButtons() {
  const botonesMonto = document.querySelectorAll('.amount-btn');
  const otroMontoInput = document.querySelector('.amount-input');

  botonesMonto.forEach(btn => {
    btn.addEventListener('click', () => {
      // Quitar clase active de todos
      botonesMonto.forEach(b => b.classList.remove('active'));
      // Poner clase active al clickeado
      btn.classList.add('active');
      // Limpiar input otro monto
      if (otroMontoInput) otroMontoInput.value = '';
    });
  });

  if (otroMontoInput) {
    otroMontoInput.addEventListener('input', () => {
      // si escribe en otro monto, quitar active de botones
      botonesMonto.forEach(b => b.classList.remove('active'));
    });
  }
}

// Manejar submit del formulario
document.querySelector('.donation-form').addEventListener('submit', function(event) {
  event.preventDefault();

  const nombreTitular = document.getElementById('nombre').value.trim();

  const botonActivo = document.querySelector('.amount-btn.active');
  let montoDonacion = 0;
  if (botonActivo) {
    montoDonacion = parseFloat(botonActivo.innerText.replace('$', ''));
  } else {
    const otroMonto = document.querySelector('.amount-input').value.trim();
    montoDonacion = otroMonto !== '' ? parseFloat(otroMonto) : 0;
  }

  const select = document.getElementById('institucion');
  const idCentro = parseInt(select.value);
  const nombreCentro = select.options[select.selectedIndex].text;

  const numeroTarjeta = document.getElementById('tarjeta').value.trim().replace(/\s/g, '');
  const bancoPago = document.getElementById('tipoTarjeta').value.trim();

  // Validaciones
  if (montoDonacion <= 0) {
    alert('Ingresa un monto válido.');
    return;
  }
  if (!nombreTitular) {
    alert('Ingresa el nombre del titular de la tarjeta.');
    return;
  }
  if (!bancoPago) {
    alert('Selecciona un banco para el pago.');
    return;
  }
  if (!select.value) {
    alert('Selecciona una institución.');
    return;
  }
  if (numeroTarjeta.length < 4) {
    alert('Ingresa un número de tarjeta válido (mínimo 4 dígitos).');
    return;
  }

  const ultimosDigitos = parseInt(numeroTarjeta.slice(-4));
  // Ajusta id_adoptante según contexto, aquí 0 o null si no tienes
  const id_adoptante = 0;

  const donacion = {
    idDonacion: 0,
    nombreDonante: nombreTitular,
    montoDonacion: montoDonacion,
    centroDonacion: nombreCentro,
    id_adoptante: id_adoptante,
    banco_pago: bancoPago,
    idCentro: idCentro,
    tipoTarjeta: "Debito", // fijo
    ultimosDigitos: ultimosDigitos
  };

  enviarDonacion(donacion);
});

function enviarDonacion(donacion) {
  const formData = new URLSearchParams();
  formData.append('datosDonacion', JSON.stringify(donacion));

  fetch('/api/donacion/saveDonacion', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
    },
    body: formData.toString()
  })
  .then(response => {
    if (!response.ok) throw new Error('Error en la red: ' + response.status);
    return response.json();
  })
  .then(data => {
    console.log('Respuesta del servidor:', data);
    alert('Donación enviada correctamente. ¡Gracias por apoyar!');
    // Aquí puedes limpiar formulario o redirigir si quieres
  })
  .catch(error => {
    console.error('Error al enviar la donación:', error);
    alert('Error al enviar la donación. Intenta más tarde.');
  });
}
