/**
 * RideX — Passenger Location & Interactive Map Engine
 * ---------------------------------------------------------------------
 * Provides the same rich map experience for passengers searching rides
 * as drivers get when creating a ride:
 *  1. Interactive Leaflet Map with draggable Pickup (Green) & Destination (Red) pins
 *  2. 1-Click GPS "Use Current Location" with live map flyTo & auto-geocoding
 *  3. Mode toggles: [📍 Set Pickup] and [🏁 Set Destination]
 *  4. ⇄ Swap Locations button
 *  5. Instant Nominatim Autocomplete synchronized with map pins
 *  6. OSRM Route drawing with distance (km) and estimated drive time
 *  7. Auto-restoration of pins if search form has existing values
 */

(function () {
    // ─── State ─────────────────────────────────────────────────────────────
    let searchMap = null;
    let searchMapMode = 'from'; // 'from' | 'to'
    let fromMarker = null;
    let toMarker = null;
    let routePolyline = null;

    let fromCoords = { lat: null, lng: null };
    let toCoords = { lat: null, lng: null };

    // ─── Icons ──────────────────────────────────────────────────────────────
    const greenPinIcon = L.icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    const redPinIcon = L.icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    // ─── Map Initialization ─────────────────────────────────────────────────
    function initPassengerMap() {
        const mapContainer = document.getElementById('searchMap');
        if (!mapContainer || searchMap) return;

        // Default view: Center of India
        searchMap = L.map('searchMap', {
            zoomControl: true,
            scrollWheelZoom: true
        }).setView([21.1458, 79.0882], 5);

        // OpenStreetMap tiles
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors',
            maxZoom: 19
        }).addTo(searchMap);

        // Click on map to place pin
        searchMap.on('click', function (e) {
            const { lat, lng } = e.latlng;
            if (searchMapMode === 'from') {
                placeFromPin(lat, lng, true);
                // Automatically switch to destination mode so user can set drop-off next
                setSearchMapMode('to');
            } else {
                placeToPin(lat, lng, true);
            }
        });

        // Check if inputs already have text (e.g. on search results reload)
        checkAndRestoreExistingInputs();

        // Invalidate map size after a tiny tick to ensure proper tile rendering
        setTimeout(() => {
            if (searchMap) searchMap.invalidateSize();
        }, 200);
    }

    // ─── Mode Switching ─────────────────────────────────────────────────────
    window.setSearchMapMode = function (mode) {
        searchMapMode = mode;
        const badge = document.getElementById('searchMapPinMode');
        const btnPickup = document.getElementById('btnSetPickupMode');
        const btnDrop = document.getElementById('btnSetDropMode');

        if (mode === 'from') {
            if (badge) {
                badge.innerHTML = '<i class="fa-solid fa-location-dot"></i> Pickup Mode';
                badge.style.background = 'rgba(34,197,94,0.15)';
                badge.style.color = '#22c55e';
                badge.style.borderColor = 'rgba(34,197,94,0.35)';
            }
            if (btnPickup) {
                btnPickup.style.background = 'rgba(34,197,94,0.2)';
                btnPickup.style.borderColor = '#22c55e';
            }
            if (btnDrop) {
                btnDrop.style.background = 'rgba(239,68,68,0.06)';
                btnDrop.style.borderColor = 'rgba(239,68,68,0.2)';
            }
        } else {
            if (badge) {
                badge.innerHTML = '<i class="fa-solid fa-flag-checkered"></i> Destination Mode';
                badge.style.background = 'rgba(239,68,68,0.15)';
                badge.style.color = '#ef4444';
                badge.style.borderColor = 'rgba(239,68,68,0.35)';
            }
            if (btnDrop) {
                btnDrop.style.background = 'rgba(239,68,68,0.2)';
                btnDrop.style.borderColor = '#ef4444';
            }
            if (btnPickup) {
                btnPickup.style.background = 'rgba(34,197,94,0.06)';
                btnPickup.style.borderColor = 'rgba(34,197,94,0.2)';
            }
        }
    };

    // ─── Place From (Pickup) Pin ─────────────────────────────────────────────
    async function placeFromPin(lat, lng, doReverseGeocode) {
        fromCoords = { lat, lng };

        if (fromMarker) searchMap.removeLayer(fromMarker);
        fromMarker = L.marker([lat, lng], { icon: greenPinIcon, draggable: true })
            .addTo(searchMap)
            .bindPopup('<b>📍 Pickup Location</b><br>Drag to adjust')
            .openPopup();

        fromMarker.on('dragend', async (e) => {
            const pos = e.target.getLatLng();
            await placeFromPin(pos.lat, pos.lng, true);
        });

        if (doReverseGeocode) {
            showGeocodingIndicator('searchFrom', true);
            const { city, display } = await reverseGeocode(lat, lng);
            showGeocodingIndicator('searchFrom', false);

            const input = document.getElementById('searchFrom');
            if (input) input.value = city || display;
            showCityBadge('from', city || display);
        }

        tryDrawPassengerRoute();
    }
    window.placeFromPin = placeFromPin;

    // ─── Place To (Destination) Pin ──────────────────────────────────────────
    async function placeToPin(lat, lng, doReverseGeocode) {
        toCoords = { lat, lng };

        if (toMarker) searchMap.removeLayer(toMarker);
        toMarker = L.marker([lat, lng], { icon: redPinIcon, draggable: true })
            .addTo(searchMap)
            .bindPopup('<b>🏁 Destination</b><br>Drag to adjust')
            .openPopup();

        toMarker.on('dragend', async (e) => {
            const pos = e.target.getLatLng();
            await placeToPin(pos.lat, pos.lng, true);
        });

        if (doReverseGeocode) {
            showGeocodingIndicator('searchTo', true);
            const { city, display } = await reverseGeocode(lat, lng);
            showGeocodingIndicator('searchTo', false);

            const input = document.getElementById('searchTo');
            if (input) input.value = city || display;
            showCityBadge('to', city || display);
        }

        tryDrawPassengerRoute();
    }
    window.placeToPin = placeToPin;

    // ─── 1-Click GPS Location Detection ──────────────────────────────────────
    window.useGPSForPassengerSearch = function () {
        if (!navigator.geolocation) {
            alert('Geolocation is not supported by your browser.');
            return;
        }

        const btn = document.getElementById('searchGpsBtn');
        const txt = document.getElementById('searchGpsTxt');
        const icon = btn ? btn.querySelector('i') : null;

        if (btn) btn.disabled = true;
        if (txt) txt.textContent = 'Locating…';
        if (icon) icon.className = 'fa-solid fa-spinner fa-spin';

        navigator.geolocation.getCurrentPosition(
            async (pos) => {
                const lat = pos.coords.latitude;
                const lng = pos.coords.longitude;

                if (searchMap) {
                    searchMap.flyTo([lat, lng], 14, { animate: true, duration: 1.2 });
                }

                await placeFromPin(lat, lng, true);

                if (txt) txt.textContent = 'GPS';
                if (icon) icon.className = 'fa-solid fa-location-crosshairs';
                if (btn) {
                    btn.disabled = false;
                    btn.style.borderColor = 'rgba(34,197,94,0.6)';
                    btn.style.color = '#22c55e';
                    setTimeout(() => {
                        btn.style.borderColor = '';
                        btn.style.color = '';
                    }, 3000);
                }

                // Switch to destination mode so user's next click sets dropoff
                setSearchMapMode('to');
            },
            (err) => {
                if (txt) txt.textContent = 'GPS';
                if (icon) icon.className = 'fa-solid fa-location-crosshairs';
                if (btn) btn.disabled = false;
                console.warn('GPS error:', err.message);
                alert('Could not access your location. Please check browser permissions or type your city.');
            },
            { enableHighAccuracy: true, timeout: 10000, maximumAge: 3000 }
        );
    };

    // ─── Swap Locations ─────────────────────────────────────────────────────
    window.swapSearchLocations = function () {
        const fromInput = document.getElementById('searchFrom');
        const toInput = document.getElementById('searchTo');
        if (!fromInput || !toInput) return;

        const tempVal = fromInput.value;
        fromInput.value = toInput.value;
        toInput.value = tempVal;

        // Swap coordinates
        const tempCoords = { ...fromCoords };
        fromCoords = { ...toCoords };
        toCoords = tempCoords;

        // Swap markers
        if (fromMarker && toMarker && searchMap) {
            const fPos = fromMarker.getLatLng();
            const tPos = toMarker.getLatLng();
            fromMarker.setLatLng(tPos);
            toMarker.setLatLng(fPos);
        } else if (fromMarker && !toMarker) {
            const fPos = fromMarker.getLatLng();
            searchMap.removeLayer(fromMarker);
            fromMarker = null;
            placeToPin(fPos.lat, fPos.lng, false);
        } else if (!fromMarker && toMarker) {
            const tPos = toMarker.getLatLng();
            searchMap.removeLayer(toMarker);
            toMarker = null;
            placeFromPin(tPos.lat, tPos.lng, false);
        }

        // Swap city badges
        showCityBadge('from', fromInput.value);
        showCityBadge('to', toInput.value);

        // Animate swap button
        const swapBtn = document.getElementById('searchSwapBtn');
        if (swapBtn) {
            swapBtn.style.transform = 'rotate(180deg)';
            setTimeout(() => { swapBtn.style.transform = ''; }, 350);
        }

        tryDrawPassengerRoute();
    };

    // ─── Reset / Clear Map ──────────────────────────────────────────────────
    window.resetSearchMap = function () {
        if (fromMarker && searchMap) { searchMap.removeLayer(fromMarker); fromMarker = null; }
        if (toMarker && searchMap) { searchMap.removeLayer(toMarker); toMarker = null; }
        if (routePolyline && searchMap) { searchMap.removeLayer(routePolyline); routePolyline = null; }
        fromCoords = { lat: null, lng: null };
        toCoords = { lat: null, lng: null };

        const banner = document.getElementById('searchRouteInfoBanner');
        if (banner) banner.style.display = 'none';

        setSearchMapMode('from');
        if (searchMap) searchMap.setView([21.1458, 79.0882], 5);
    };

    // ─── Reverse Geocoding via Nominatim ────────────────────────────────────
    async function reverseGeocode(lat, lng) {
        try {
            const url = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}&zoom=14&addressdetails=1`;
            const res = await fetch(url, { headers: { 'Accept-Language': 'en' } });
            const data = await res.json();
            const addr = data.address || {};
            const city = addr.city || addr.town || addr.village || addr.municipality || addr.suburb || addr.county || addr.state_district || addr.state || '';
            const display = data.display_name || `${lat.toFixed(4)}, ${lng.toFixed(4)}`;
            return { city, display };
        } catch (e) {
            console.warn('Reverse geocode error:', e);
            return { city: '', display: `${lat.toFixed(4)}, ${lng.toFixed(4)}` };
        }
    }

    // ─── OSRM Route Drawing ─────────────────────────────────────────────────
    async function tryDrawPassengerRoute() {
        if (!searchMap) return;
        if (!fromCoords.lat || !fromCoords.lng || !toCoords.lat || !toCoords.lng) return;

        if (routePolyline) {
            searchMap.removeLayer(routePolyline);
            routePolyline = null;
        }

        try {
            const url = `https://router.project-osrm.org/route/v1/driving/${fromCoords.lng},${fromCoords.lat};${toCoords.lng},${toCoords.lat}?overview=full&geometries=geojson`;
            const res = await fetch(url);
            const data = await res.json();

            if (data.code === 'Ok' && data.routes && data.routes[0]) {
                const route = data.routes[0];
                const distKm = (route.distance / 1000).toFixed(1);
                const durMins = Math.round(route.duration / 60);
                const durText = durMins >= 60
                    ? `${Math.floor(durMins / 60)}h ${durMins % 60}m`
                    : `${durMins} min`;

                const coords = route.geometry.coordinates.map(c => [c[1], c[0]]);
                routePolyline = L.polyline(coords, {
                    color: '#10b981',
                    weight: 5,
                    opacity: 0.8
                }).addTo(searchMap);

                searchMap.fitBounds(routePolyline.getBounds(), { padding: [40, 40] });

                // Update route preview banner
                const banner = document.getElementById('searchRouteInfoBanner');
                if (banner) {
                    const distEl = document.getElementById('searchRouteDist');
                    const durEl = document.getElementById('searchRouteDur');
                    if (distEl) distEl.textContent = distKm + ' km';
                    if (durEl) durEl.textContent = durText;
                    banner.style.display = 'block';
                }
            }
        } catch (e) {
            console.warn('OSRM routing failed:', e);
        }
    }

    // ─── City Badge & Indicator Helpers ─────────────────────────────────────
    function showCityBadge(which, text) {
        if (!text) return;
        const badge = document.getElementById(which === 'from' ? 'fromCityBadge' : 'toCityBadge');
        const span = document.getElementById(which === 'from' ? 'fromCityText' : 'toCityText');
        if (badge && span) {
            span.textContent = text;
            badge.style.display = 'inline-flex';
        }
    }

    function showGeocodingIndicator(inputId, isLoading) {
        const input = document.getElementById(inputId);
        if (!input) return;
        if (isLoading) {
            input.setAttribute('data-orig-placeholder', input.placeholder);
            input.placeholder = 'Reverse-geocoding location…';
        } else {
            const orig = input.getAttribute('data-orig-placeholder');
            if (orig) input.placeholder = orig;
        }
    }

    // ─── Autocomplete Setup ─────────────────────────────────────────────────
    let debounceTimers = {};
    function attachPassengerAutocomplete(inputId, suggestionListId, type) {
        const input = document.getElementById(inputId);
        const list = document.getElementById(suggestionListId);
        if (!input || !list) return;

        input.addEventListener('input', function () {
            clearTimeout(debounceTimers[inputId]);
            const q = this.value.trim();
            if (q.length < 2) {
                list.classList.add('hidden');
                list.innerHTML = '';
                return;
            }

            debounceTimers[inputId] = setTimeout(async () => {
                try {
                    const res = await fetch(
                        `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(q)}&countrycodes=IN&limit=6&addressdetails=1`,
                        { headers: { 'Accept-Language': 'en' } }
                    );
                    const data = await res.json();
                    list.innerHTML = '';
                    if (!data || !data.length) {
                        list.classList.add('hidden');
                        return;
                    }

                    data.forEach(item => {
                        const li = document.createElement('li');
                        li.className = 'rx-suggestion-item';
                        const addr = item.address || {};
                        const city = addr.city || addr.town || addr.village || addr.municipality || addr.suburb || addr.state_district || addr.state || '';
                        const lat = parseFloat(item.lat);
                        const lon = parseFloat(item.lon);

                        li.innerHTML = `
                            <div style="display:flex;align-items:center;gap:10px;">
                                <i class="fa-solid fa-location-dot" style="color:${type === 'from' ? '#10b981' : '#ef4444'};"></i>
                                <div>
                                    <div style="font-weight:600;color:var(--rx-text-primary);font-size:0.85rem;">${city || item.display_name.split(',')[0]}</div>
                                    <div style="font-size:0.73rem;color:var(--rx-text-muted);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width:280px;">${item.display_name}</div>
                                </div>
                            </div>
                        `;

                        li.addEventListener('mousedown', (e) => {
                            e.preventDefault();
                            input.value = city || item.display_name.split(',')[0];
                            list.classList.add('hidden');
                            showCityBadge(type, input.value);

                            if (searchMap) {
                                searchMap.flyTo([lat, lon], 12, { animate: true, duration: 1 });
                            }

                            if (type === 'from') {
                                placeFromPin(lat, lon, false);
                                setSearchMapMode('to');
                            } else {
                                placeToPin(lat, lon, false);
                            }
                        });

                        list.appendChild(li);
                    });

                    list.classList.remove('hidden');
                } catch (err) {
                    list.classList.add('hidden');
                }
            }, 300);
        });

        document.addEventListener('click', (e) => {
            if (!input.contains(e.target)) list.classList.add('hidden');
        });
    }

    // ─── Restore Existing Input Pins If Text Is Present ─────────────────────
    async function checkAndRestoreExistingInputs() {
        const fromVal = document.getElementById('searchFrom')?.value?.trim();
        const toVal = document.getElementById('searchTo')?.value?.trim();

        if (fromVal) {
            showCityBadge('from', fromVal);
            geocodeAndPin(fromVal, 'from');
        }
        if (toVal) {
            showCityBadge('to', toVal);
            geocodeAndPin(toVal, 'to');
        }
    }

    async function geocodeAndPin(query, type) {
        try {
            const res = await fetch(
                `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}&countrycodes=IN&limit=1`,
                { headers: { 'Accept-Language': 'en' } }
            );
            const data = await res.json();
            if (data && data.length) {
                const lat = parseFloat(data[0].lat);
                const lon = parseFloat(data[0].lon);
                if (type === 'from') {
                    placeFromPin(lat, lon, false);
                } else {
                    placeToPin(lat, lon, false);
                }
            }
        } catch (_) {}
    }

    // ─── Quick Date Fillers ─────────────────────────────────────────────────
    window.setSearchDateQuick = function (offsetDays) {
        const dateInput = document.getElementById('date');
        if (!dateInput) return;
        const d = new Date();
        d.setDate(d.getDate() + offsetDays);
        dateInput.value = d.toISOString().split('T')[0];
    };

    // ─── Initialize Everything ──────────────────────────────────────────────
    window.addEventListener('DOMContentLoaded', () => {
        initPassengerMap();
        attachPassengerAutocomplete('searchFrom', 'searchFrom-suggestions', 'from');
        attachPassengerAutocomplete('searchTo', 'searchTo-suggestions', 'to');

        // Set default date to today if empty
        const dateInput = document.getElementById('date');
        if (dateInput && !dateInput.value) {
            dateInput.value = new Date().toISOString().split('T')[0];
        }
    });

})();
