// Initialize Leaflet map safely for ride route display
(function() {
    function initMap() {
        const mapContainer = document.getElementById('map');
        if (!mapContainer || typeof L === 'undefined') {
            return;
        }

        try {
            let sLat = 28.6139;
            let sLon = 77.2090;

            if (typeof rideCoordinates !== 'undefined' && rideCoordinates.sourceLat && !isNaN(parseFloat(rideCoordinates.sourceLat))) {
                sLat = parseFloat(rideCoordinates.sourceLat);
                sLon = parseFloat(rideCoordinates.sourceLon);
            }

            const map = L.map('map').setView([sLat, sLon], 8);

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: 'Map data © OpenStreetMap contributors'
            }).addTo(map);

            if (typeof rideCoordinates !== 'undefined' &&
                rideCoordinates.sourceLat && rideCoordinates.sourceLon &&
                rideCoordinates.destinationLat && rideCoordinates.destinationLon &&
                !isNaN(parseFloat(rideCoordinates.destinationLat))) {

                const source = L.latLng(sLat, sLon);
                const destination = L.latLng(parseFloat(rideCoordinates.destinationLat), parseFloat(rideCoordinates.destinationLon));

                if (typeof L.Routing !== 'undefined') {
                    L.Routing.control({
                        waypoints: [source, destination],
                        routeWhileDragging: false,
                        addWaypoints: false,
                        draggableWaypoints: false,
                        fitSelectedRoutes: true,
                        createMarker: function () { return null; }
                    }).addTo(map);
                }

                const bounds = L.latLngBounds([source, destination]);
                map.fitBounds(bounds, { padding: [50, 50] });
            }
        } catch (e) {
            console.warn("Leaflet map initialization skipped or caught error:", e);
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initMap);
    } else {
        initMap();
    }
})();
