/**
 * RideX — Address Autocomplete  (Phase 1 - upgraded)
 * ---------------------------------------------------
 * Uses Nominatim for address search.
 * When user selects a result, calls window.setLocationFromSuggestion()
 * from locationAutomation.js — which fills lat/lon/city/address in one go.
 * This eliminates the second geocode fetch that coordinates.js used to do.
 */

import cities from './cities.js';

// ─── Helpers ──────────────────────────────────────────────────────────────────
function updateActive(items, idx) {
    items.forEach(li => li.classList.remove('rx-suggestion-active'));
    if (items[idx]) {
        items[idx].classList.add('rx-suggestion-active');
        items[idx].scrollIntoView({ block: 'nearest' });
    }
}

// ─── Address Autocomplete (Nominatim) ─────────────────────────────────────────
let addressTimer = null;

function setupAddressAutocomplete(inputId, listId, which) {
    const input = document.getElementById(inputId);
    const list  = document.getElementById(listId);
    if (!input || !list) return;

    let activeIndex = -1;

    input.addEventListener('input', () => {
        const value = input.value.trim();
        clearTimeout(addressTimer);
        list.innerHTML = '';
        activeIndex = -1;

        if (value.length < 3) {
            list.classList.add('hidden');
            return;
        }

        // Debounce 350ms
        addressTimer = setTimeout(async () => {
            try {
                const url = `https://nominatim.openstreetmap.org/search?format=json&limit=6&countrycodes=in&addressdetails=1&q=${encodeURIComponent(value)}`;
                const res  = await fetch(url);
                const data = await res.json();

                list.innerHTML = '';
                activeIndex = -1;

                if (!data.length) { list.classList.add('hidden'); return; }

                data.forEach((place, idx) => {
                    const li = document.createElement('li');

                    // Extract readable label
                    const city = place.address
                        ? (place.address.city || place.address.town || place.address.village || place.address.state_district || '')
                        : '';
                    const shortName = place.display_name.split(',').slice(0, 3).join(',');
                    li.innerHTML = `<span>${shortName}</span>${city ? `<small style="display:block;font-size:0.72rem;color:var(--rx-text-muted);">${city}</small>` : ''}`;
                    li.dataset.index = idx;

                    li.addEventListener('click', () => {
                        input.value = shortName;
                        list.classList.add('hidden');

                        // Let locationAutomation.js handle coordinates + city + map pin
                        if (typeof window.setLocationFromSuggestion === 'function') {
                            window.setLocationFromSuggestion(which, place);
                        }
                    });

                    list.appendChild(li);
                });

                list.classList.remove('hidden');
            } catch (err) {
                console.error('Address autocomplete error:', err);
            }
        }, 350);
    });

    // Keyboard navigation
    input.addEventListener('keydown', (e) => {
        const items = list.querySelectorAll('li');
        if (!items.length) return;

        if (e.key === 'ArrowDown') {
            e.preventDefault();
            activeIndex = Math.min(activeIndex + 1, items.length - 1);
            updateActive(items, activeIndex);
        } else if (e.key === 'ArrowUp') {
            e.preventDefault();
            activeIndex = Math.max(activeIndex - 1, 0);
            updateActive(items, activeIndex);
        } else if (e.key === 'Enter' && activeIndex >= 0) {
            e.preventDefault();
            items[activeIndex].click();
        } else if (e.key === 'Escape') {
            list.classList.add('hidden');
        }
    });

    document.addEventListener('click', (e) => {
        if (!input.contains(e.target) && !list.contains(e.target)) {
            list.classList.add('hidden');
        }
    });
}

// ─── Init ─────────────────────────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
    // Address autocomplete for both fields — each specifies 'from' or 'to'
    setupAddressAutocomplete('fromAddress', 'fromAddress-suggestions', 'from');
    setupAddressAutocomplete('toAddress',   'toAddress-suggestions',   'to');
});
