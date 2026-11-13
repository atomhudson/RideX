/**
 * RideX — Location Automation Engine  (Phase 1)
 * -----------------------------------------------
 * Features:
 *  1. 1-Click GPS "Use Current Location"
 *  2. Smart address autocomplete that extracts lat/lon/city in ONE fetch
 *  3. Interactive Leaflet map click-to-pin (Pickup & Drop modes)
 *  4. ⇄ Swap Route button
 *  5. Live OSRM route drawing with distance, ETA, and auto-price suggestion
 */

// ─── State ─────────────────────────────────────────────────────────────────
const RATE_PER_KM = 6.5; // ₹ per km (adjustable)
let mapMode = 'from';     // 'from' | 'to'

let createMap = null;
let fromMarker = null;
let toMarker   = null;
let routePolyline = null;
let routeControl  = null;

let suggestedPriceValue = 0;

// ─── Icons ──────────────────────────────────────────────────────────────────
const greenIcon = L.icon({
    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-shadow.png',
    iconSize: [25, 41], iconAnchor: [12, 41], popupAnchor: [1, -34],
});
const redIcon = L.icon({
    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-shadow.png',
    iconSize: [25, 41], iconAnchor: [12, 41], popupAnchor: [1, -34],
});

// ─── Init Map ────────────────────────────────────────────────────────────────
window.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('createMap')) {
        createMap = L.map('createMap').setView([20.5937, 78.9629], 5); // India center

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors',
            maxZoom: 19
        }).addTo(createMap);

        // Click to place pin
        createMap.on('click', function (e) {
            const { lat, lng } = e.latlng;
            if (mapMode === 'from') {
                placeFromPin(lat, lng, true); // true = do reverse geocode
            } else {
                placeToPin(lat, lng, true);
            }
        });

        // Restore pins if editing an existing ride
        const fLat = parseFloat(document.getElementById('fromLat')?.value);
        const fLng = parseFloat(document.getElementById('fromLng')?.value);
        const tLat = parseFloat(document.getElementById('toLat')?.value);
        const tLng = parseFloat(document.getElementById('toLng')?.value);
        if (fLat && fLng) placeFromPin(fLat, fLng, false);
        if (tLat && tLng) placeToPin(tLat, tLng, false);
        if (fLat && fLng && tLat && tLng) drawRoute(fLat, fLng, tLat, tLng);
    }
});

// ─── Map Mode ────────────────────────────────────────────────────────────────
function setMapMode(mode) {
    mapMode = mode;
    const badge = document.getElementById('mapPinMode');
    if (!badge) return;
    if (mode === 'from') {
        badge.textContent = '📍 Pickup mode';
        badge.style.background = 'rgba(34,197,94,0.12)';
        badge.style.color = '#22c55e';
        badge.style.border = '1px solid rgba(34,197,94,0.3)';
    } else {
        badge.textContent = '🏁 Drop mode';
        badge.style.background = 'rgba(239,68,68,0.1)';
        badge.style.color = '#ef4444';
        badge.style.border = '1px solid rgba(239,68,68,0.25)';
    }
}
window.setMapMode = setMapMode;

// ─── Place Pins ──────────────────────────────────────────────────────────────
async function placeFromPin(lat, lng, doReverseGeocode) {
    if (fromMarker) createMap.removeLayer(fromMarker);
    fromMarker = L.marker([lat, lng], { icon: greenIcon, draggable: true })
        .addTo(createMap).bindPopup('📍 Pickup').openPopup();

    fromMarker.on('dragend', async (e) => {
        const pos = e.target.getLatLng();
        await placeFromPin(pos.lat, pos.lng, true);
    });

    // Set hidden fields
    setField('fromLat', lat);
    setField('fromLng', lng);

    if (doReverseGeocode) {
        const { address, display } = await reverseGeocode(lat, lng);
        setField('fromAddress', display, true);
        setField('from', extractCity(address));
        showCityBadge('from', extractCity(address));
    }

    tryDrawRoute();
}

async function placeToPin(lat, lng, doReverseGeocode) {
    if (toMarker) createMap.removeLayer(toMarker);
    toMarker = L.marker([lat, lng], { icon: redIcon, draggable: true })
        .addTo(createMap).bindPopup('🏁 Drop-off').openPopup();

    toMarker.on('dragend', async (e) => {
        const pos = e.target.getLatLng();
        await placeToPin(pos.lat, pos.lng, true);
    });

    setField('toLat', lat);
    setField('toLng', lng);

    if (doReverseGeocode) {
        const { address, display } = await reverseGeocode(lat, lng);
        setField('toAddress', display, true);
        setField('to', extractCity(address));
        showCityBadge('to', extractCity(address));
    }

    tryDrawRoute();
}

// ─── GPS Current Location ─────────────────────────────────────────────────────
function useCurrentLocation() {
    if (!navigator.geolocation) {
        alert('Geolocation is not supported by your browser.');
        return;
    }
    const btn = document.getElementById('gpsBtn');
    if (btn) { btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Detecting…'; btn.disabled = true; }

    navigator.geolocation.getCurrentPosition(
        async (pos) => {
            const lat = pos.coords.latitude;
            const lng = pos.coords.longitude;
            if (createMap) {
                createMap.setView([lat, lng], 13);
            }
            await placeFromPin(lat, lng, true);
            if (btn) { btn.innerHTML = '<i class="fa-solid fa-check"></i> Location Set!'; btn.disabled = false; }
            setTimeout(() => {
                if (btn) btn.innerHTML = '<i class="fa-solid fa-location-crosshairs"></i> Use Current Location';
            }, 3000);
        },
        (err) => {
            if (btn) { btn.innerHTML = '<i class="fa-solid fa-location-crosshairs"></i> Use Current Location'; btn.disabled = false; }
            console.warn('GPS error:', err.message);
            alert('Could not get your location. Please allow location access in your browser.');
        },
        { timeout: 10000, enableHighAccuracy: true }
    );
}
window.useCurrentLocation = useCurrentLocation;

// ─── Swap Locations ───────────────────────────────────────────────────────────
function swapLocations() {
    const fromAddr = getField('fromAddress');
    const fromCity = getField('from');
    const fromLat  = getField('fromLat');
    const fromLng  = getField('fromLng');

    const toAddr = getField('toAddress');
    const toCity = getField('to');
    const toLat  = getField('toLat');
    const toLng  = getField('toLng');

    setField('fromAddress', toAddr, true);
    setField('from', toCity);
    setField('fromLat', toLat);
    setField('fromLng', toLng);

    setField('toAddress', fromAddr, true);
    setField('to', fromCity);
    setField('toLat', fromLat);
    setField('toLng', fromLng);

    // Update city badges
    showCityBadge('from', toCity);
    showCityBadge('to', fromCity);

    // Swap markers
    if (fromMarker && toMarker && createMap) {
        const fPos = fromMarker.getLatLng();
        const tPos = toMarker.getLatLng();
        fromMarker.setLatLng(tPos);
        toMarker.setLatLng(fPos);
    }

    tryDrawRoute();

    // Animate button
    const btn = document.getElementById('swapBtn');
    if (btn) { btn.style.transform = 'rotate(180deg)'; setTimeout(() => { btn.style.transform = ''; }, 400); }
}
window.swapLocations = swapLocations;

// ─── Route Drawing (OSRM) ─────────────────────────────────────────────────────
async function tryDrawRoute() {
    const fLat = parseFloat(getField('fromLat'));
    const fLng = parseFloat(getField('fromLng'));
    const tLat = parseFloat(getField('toLat'));
    const tLng = parseFloat(getField('toLng'));

    if (!fLat || !fLng || !tLat || !tLng) return;
    await drawRoute(fLat, fLng, tLat, tLng);
}
window.tryDrawRoute = tryDrawRoute;


async function drawRoute(fLat, fLng, tLat, tLng) {
    if (!createMap) return;

    // Remove old route
    if (routePolyline) { createMap.removeLayer(routePolyline); routePolyline = null; }

    try {
        // Use OSRM public routing API (free, no API key)
        const url = `https://router.project-osrm.org/route/v1/driving/${fLng},${fLat};${tLng},${tLat}?overview=full&geometries=geojson`;
        const res = await fetch(url);
        const data = await res.json();

        if (data.code !== 'Ok' || !data.routes || !data.routes[0]) {
            console.warn('OSRM routing failed');
            return;
        }

        const route   = data.routes[0];
        const distKm  = (route.distance / 1000).toFixed(1);
        const durMins = Math.round(route.duration / 60);
        const durText = durMins >= 60
            ? `${Math.floor(durMins / 60)}h ${durMins % 60}m`
            : `${durMins} min`;

        // Draw polyline
        const coords = route.geometry.coordinates.map(c => [c[1], c[0]]);
        routePolyline = L.polyline(coords, {
            color: '#6366f1', weight: 4, opacity: 0.75, dashArray: null
        }).addTo(createMap);

        // Fit map to route
        createMap.fitBounds(routePolyline.getBounds(), { padding: [40, 40] });

        // Auto-price calculation
        const seats = parseInt(document.getElementById('seatValue')?.textContent || '1', 10);
        const price = Math.round((parseFloat(distKm) * RATE_PER_KM) / seats);
        suggestedPriceValue = price;

        // Update banner
        const banner = document.getElementById('routeInfoBanner');
        if (banner) {
            document.getElementById('routeDistance').textContent = distKm;
            document.getElementById('routeDuration').textContent = durText;
            document.getElementById('suggestedPrice').textContent = `₹${price} /seat`;
            banner.style.display = 'block';
        }
    } catch (e) {
        console.warn('Route draw error:', e);
    }
}

// ─── Apply Suggested Price ────────────────────────────────────────────────────
function applyPrice() {
    if (suggestedPriceValue > 0) {
        setField('price', suggestedPriceValue);
        const banner = document.getElementById('routeInfoBanner');
        if (banner) {
            const applyBtn = banner.querySelector('button');
            if (applyBtn) { applyBtn.textContent = '✓ Applied!'; setTimeout(() => { applyBtn.textContent = 'Apply'; }, 2000); }
        }
    }
}
window.applyPrice = applyPrice;

// ─── Reverse Geocode (Nominatim) ─────────────────────────────────────────────
async function reverseGeocode(lat, lng) {
    try {
        const url = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`;
        const res = await fetch(url, { headers: { 'Accept-Language': 'en' } });
        const data = await res.json();
        return { address: data.address || {}, display: data.display_name || `${lat.toFixed(4)}, ${lng.toFixed(4)}` };
    } catch (e) {
        return { address: {}, display: `${lat.toFixed(4)}, ${lng.toFixed(4)}` };
    }
}

// ─── City Extraction ──────────────────────────────────────────────────────────
function extractCity(address) {
    return address.city
        || address.town
        || address.village
        || address.county
        || address.state_district
        || address.state
        || '';
}

// ─── City Badge ───────────────────────────────────────────────────────────────
function showCityBadge(which, city) {
    if (!city) return;
    const badgeEl = document.getElementById(which === 'from' ? 'fromCityBadge' : 'toCityBadge');
    const textEl  = document.getElementById(which === 'from' ? 'fromCityText'  : 'toCityText');
    if (badgeEl && textEl) {
        textEl.textContent = city;
        badgeEl.style.display = 'block';
    }
}

// ─── Field Helpers ────────────────────────────────────────────────────────────
function setField(id, value, isInput = false) {
    const el = document.getElementById(id);
    if (!el) return;
    el.value = value;
    if (isInput) el.dispatchEvent(new Event('input', { bubbles: true }));
}
function getField(id) {
    return document.getElementById(id)?.value || '';
}

// ─── Expose for autocomplete.js to call when user selects a suggestion ────────
/**
 * Called by createRideAutocomplete.js when user picks a Nominatim suggestion.
 * @param {string} which  'from' | 'to'
 * @param {object} place  Nominatim result object { lat, lon, display_name, address }
 */
window.setLocationFromSuggestion = async function (which, place) {
    const lat = parseFloat(place.lat);
    const lng = parseFloat(place.lon);
    const city = extractCity(place.address || {});
    const display = place.display_name;

    if (which === 'from') {
        setField('fromAddress', display, true);
        setField('from', city);
        setField('fromLat', lat);
        setField('fromLng', lng);
        showCityBadge('from', city);
        if (createMap) await placeFromPin(lat, lng, false); // no extra reverse geocode needed
    } else {
        setField('toAddress', display, true);
        setField('to', city);
        setField('toLat', lat);
        setField('toLng', lng);
        showCityBadge('to', city);
        if (createMap) await placeToPin(lat, lng, false);
    }

    // Also update seat-based price whenever seat changes
    document.getElementById('seat')?.addEventListener('change', () => tryDrawRoute());
};

// Re-compute price when seats change
['seatValue'].forEach(id => {
    const el = document.getElementById(id);
    if (el) {
        const observer = new MutationObserver(() => tryDrawRoute());
        observer.observe(el, { childList: true });
    }
});
