
mapboxgl.accessToken = "pk.eyJ1IjoiYXVndXN0dGUtYW5ndWxsaW5pNjkiLCJhIjoiY2x6M2Rwc3dyMDdpOTJqcHc1amlxNWQxMyJ9.s1gZ9OAstoclyciBDKZ5Ug";

const apiKeyHERE = "LZTNryiMeQ4IPZ-ZZNHLBqCC00GexjyBR2I98CM5Sro";
const direccion = "Río Verde 243, La Luz, 37458 León de los Aldama, Gto., México";

fetch(`https://geocode.search.hereapi.com/v1/geocode?q=${encodeURIComponent(direccion)}&apiKey=${apiKeyHERE}`)
  .then(response => response.json())
  .then(data => {
    if (data.items && data.items.length > 0) {
      const lat = data.items[0].position.lat;
      const lng = data.items[0].position.lng;

      // Crear el mapa y centrarlo en la ubicación obtenida
      const mapa = new mapboxgl.Map({
        container: "mapa",
         style: "mapbox://styles/mapbox/streets-v12",
        center: [lng, lat],
        zoom: 15
      });

      new mapboxgl.Marker({ color: "green", rotation: 45 })
        .setLngLat([lng, lat])
        .setPopup(new mapboxgl.Popup().setHTML("<b>Centro de Control y Bienestar Animal de León</b><br>Río Verde 243"))
        .addTo(mapa);
      mapa.addControl(new mapboxgl.NavigationControl());

      mapa.addControl(new mapboxgl.GeolocateControl({
        positionOptions: {
          enableHighAccuracy: true
        },
        trackUserLocation: true
      }));
 window.addEventListener('resize', () => {
  map.resize();
});

    } else {
      console.error("No se encontró la ubicación para la dirección dada.");
      document.getElementById("mapa").innerText = "No se pudo cargar el mapa.";
    }
  })
  .catch(error => {
    console.error("Error al obtener las coordenadas:", error);
    document.getElementById("mapa").innerText = "Error cargando el mapa.";
  });
