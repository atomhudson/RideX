/**
 * RideX — Coordinates fallback (Phase 1)
 * ----------------------------------------
 * Primary coordinate setting is now handled by locationAutomation.js.
 * This file serves as a final safety net on form submit —
 * if lat/lng are still empty when the user hits "Publish Ride",
 * it tries one geocode call based on the typed address text.
 */

async function geocode(fullAddress) {
    if (!fullAddress || fullAddress.trim().length === 0) return null;
    try {
        const url = `https://nominatim.openstreetmap.org/search?format=json&limit=1&countrycodes=in&addressdetails=1&q=${encodeURIComponent(fullAddress.trim())}`;
        const response = await fetch(url);
        const data = await response.json();
        return (data && data.length > 0) ? data[0] : null;
    } catch (e) {
        console.warn('Geocoding fallback failed:', e);
        return null;
    }
}

function extractCity(address) {
    if (!address) return '';
    return address.city || address.town || address.village || address.county || address.state_district || address.state || '';
}

// Submit-time safety: fill any missing coordinates before POST
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('publishRideForm');
    if (!form) return;

    form.addEventListener('submit', async function (e) {
        const fromLat = document.getElementById('fromLat')?.value;
        const fromLng = document.getElementById('fromLng')?.value;
        const toLat   = document.getElementById('toLat')?.value;
        const toLng   = document.getElementById('toLng')?.value;

        // Only geocode if coordinates are still missing
        if (!fromLat || !fromLng) {
            const addr = document.getElementById('fromAddress')?.value;
            if (addr) {
                const result = await geocode(addr);
                if (result) {
                    document.getElementById('fromLat').value = result.lat;
                    document.getElementById('fromLng').value = result.lon;
                    const city = extractCity(result.address);
                    if (city && document.getElementById('from')) document.getElementById('from').value = city;
                }
            }
        }

        if (!toLat || !toLng) {
            const addr = document.getElementById('toAddress')?.value;
            if (addr) {
                const result = await geocode(addr);
                if (result) {
                    document.getElementById('toLat').value = result.lat;
                    document.getElementById('toLng').value = result.lon;
                    const city = extractCity(result.address);
                    if (city && document.getElementById('to')) document.getElementById('to').value = city;
                }
            }
        }
    });
});