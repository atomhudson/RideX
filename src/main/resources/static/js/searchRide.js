import cities from './cities.js';

function setupAutocomplete(inputId, listId) {
    const input = document.getElementById(inputId);
    const list = document.getElementById(listId);

    input.addEventListener('input', () => {
        const value = input.value.trim().toLowerCase();
        list.innerHTML = '';

        if (!value) {
            list.classList.add('hidden');
            return;
        }

        const matches = cities.filter(city =>
            city.name.toLowerCase().startsWith(value)
        );

        if (matches.length === 0) {
            list.classList.add('hidden');
            return;
        }

        matches.forEach(city => {
            const li = document.createElement('li');
            li.textContent = `${city.name}, ${city.state}`;
            li.className = 'px-4 py-2 hover:bg-gray-200 cursor-pointer';
            li.addEventListener('click', () => {
                input.value = `${city.name}, ${city.state}`;
                list.classList.add('hidden');
            });
            list.appendChild(li);
        });

        list.classList.remove('hidden');
    });

    // Close suggestions when clicking outside input or list
    document.addEventListener('click', (e) => {
        if (!input.contains(e.target) && !list.contains(e.target)) {
            list.classList.add('hidden');
        }
    });
}

// Initialize autocomplete for both fields
setupAutocomplete('from', 'from-suggestions');
setupAutocomplete('to', 'to-suggestions');
