// Initialize Leaflet map
const map = L.map('map').setView([rideCoordinates.sourceLat, rideCoordinates.sourceLon], 8); // Set center based on ride coordinates

// Add OpenStreetMap tile layer
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Map data © OpenStreetMap contributors'
}).addTo(map);

// Update map with coordinates from rideCoordinates object
function updateMapWithCoords(sourceLat, sourceLon, destinationLat, destinationLon) {
    const source = L.latLng(sourceLat, sourceLon);
    const destination = L.latLng(destinationLat, destinationLon);

    // Display the route using Leaflet Routing Machine
    L.Routing.control({
        waypoints: [source, destination],
        routeWhileDragging: false,
        addWaypoints: false,
        draggableWaypoints: false,
        fitSelectedRoutes: true,
        createMarker: function () { return null; } // optional: no markers
    }).addTo(map);

    // Fit map to bounds
    const bounds = L.latLngBounds([source, destination]);
    map.fitBounds(bounds, { padding: [50, 50] });
}

// Safely load map only if coordinates are available
if (typeof rideCoordinates !== 'undefined' &&
    rideCoordinates.sourceLat && rideCoordinates.sourceLon &&
    rideCoordinates.destinationLat && rideCoordinates.destinationLon) {
    updateMapWithCoords(
        rideCoordinates.sourceLat,
        rideCoordinates.sourceLon,
        rideCoordinates.destinationLat,
        rideCoordinates.destinationLon
    );
} else {
    console.warn("Coordinates not found in rideCoordinates object.");
}
