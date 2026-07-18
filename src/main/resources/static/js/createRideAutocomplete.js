import cities from './cities.js';

// ── City Autocomplete (local filter against cities.js) ──────────────
function setupCityAutocomplete(inputId, listId) {
    const input = document.getElementById(inputId);
    const list  = document.getElementById(listId);
    if (!input || !list) return;

    let activeIndex = -1;

    input.addEventListener('input', () => {
        const value = input.value.trim().toLowerCase();
        list.innerHTML = '';
        activeIndex = -1;

        if (!value) {
            list.classList.add('hidden');
            return;
        }

        const matches = cities
            .filter(c => c.name.toLowerCase().startsWith(value))
            .slice(0, 30);               // cap to 30 for performance

        if (matches.length === 0) {
            list.classList.add('hidden');
            return;
        }

        matches.forEach((city, idx) => {
            const li = document.createElement('li');
            li.textContent = `${city.name}, ${city.state}`;
            li.dataset.index = idx;
            li.addEventListener('click', () => {
                input.value = `${city.name}, ${city.state}`;
                list.classList.add('hidden');
                input.dispatchEvent(new Event('change'));
            });
            list.appendChild(li);
        });

        list.classList.remove('hidden');
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

// ── Address Autocomplete (Nominatim) ────────────────────────────────
let addressTimer = null;

function setupAddressAutocomplete(inputId, listId) {
    const input = document.getElementById(inputId);
    const list  = document.getElementById(listId);
    if (!input || !list) return;

    let activeIndex = -1;

    input.addEventListener('input', () => {
        const value = input.value.trim();
        clearTimeout(addressTimer);

        if (value.length < 3) {
            list.innerHTML = '';
            list.classList.add('hidden');
            return;
        }

        // Debounce 350ms to avoid spamming Nominatim
        addressTimer = setTimeout(async () => {
            try {
                const url = `https://nominatim.openstreetmap.org/search?format=json&limit=6&countrycodes=in&q=${encodeURIComponent(value)}`;
                const res = await fetch(url);
                const data = await res.json();

                list.innerHTML = '';
                activeIndex = -1;

                if (!data.length) {
                    list.classList.add('hidden');
                    return;
                }

                data.forEach((place, idx) => {
                    const li = document.createElement('li');
                    li.textContent = place.display_name;
                    li.dataset.index = idx;
                    li.addEventListener('click', () => {
                        input.value = place.display_name;
                        list.classList.add('hidden');
                        input.dispatchEvent(new Event('change'));
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

// ── Helper ──────────────────────────────────────────────────────────
function updateActive(items, idx) {
    items.forEach(li => li.classList.remove('rx-suggestion-active'));
    if (items[idx]) {
        items[idx].classList.add('rx-suggestion-active');
        items[idx].scrollIntoView({ block: 'nearest' });
    }
}

// ── Init ────────────────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
    setupCityAutocomplete('from',        'from-suggestions');
    setupCityAutocomplete('to',          'to-suggestions');
    setupAddressAutocomplete('fromAddress', 'fromAddress-suggestions');
    setupAddressAutocomplete('toAddress',   'toAddress-suggestions');
});
