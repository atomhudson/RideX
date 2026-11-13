document.addEventListener('DOMContentLoaded', function() {
    const rideList = document.getElementById('rideList');
    if (!rideList) return;

    const clearFiltersBtn = document.getElementById('clearFilters');
    const sortInputs = document.querySelectorAll('input[data-filter="sort"]');
    const timeInputs = document.querySelectorAll('input[data-filter="time"]');
    const amenityInputs = document.querySelectorAll('input[data-filter="amenity"]');

    // Store all rides for filtering
    let allRides = Array.from(rideList.children);

    // Function to filter rides based on selected criteria
    function filterRides() {
        const selectedSort = document.querySelector('input[data-filter="sort"]:checked')?.value;
        const selectedTimes = Array.from(timeInputs)
            .filter(input => input.checked)
            .map(input => input.value);
        const selectedAmenities = Array.from(amenityInputs)
            .filter(input => input.checked)
            .map(input => input.value);

        // Filter rides
        let filteredRides = allRides.filter(ride => {
            // Filter by time
            if (selectedTimes.length > 0) {
                const rideTime = ride.dataset.time || '';
                if (rideTime) {
                    const timeMatch = selectedTimes.some(timeRange => {
                        const [start, end] = timeRange.split('-');
                        return rideTime >= start && rideTime <= end;
                    });
                    if (!timeMatch) return false;
                }
            }

            // Filter by amenities
            if (selectedAmenities.length > 0) {
                const rideAmenities = ride.dataset.amenities?.split(',').filter(Boolean) || [];
                if (!selectedAmenities.every(amenity => rideAmenities.includes(amenity))) {
                    return false;
                }
            }

            return true;
        });

        // Sort rides
        if (selectedSort) {
            filteredRides.sort((a, b) => {
                switch (selectedSort) {
                    case 'earliest': {
                        const aTime = a.dataset.time || '';
                        const bTime = b.dataset.time || '';
                        return aTime.localeCompare(bTime);
                    }
                    case 'lowest': {
                        const aPrice = parseFloat(a.dataset.price || '0');
                        const bPrice = parseFloat(b.dataset.price || '0');
                        return aPrice - bPrice;
                    }
                    case 'shortest': {
                        const aDuration = parseFloat(a.dataset.duration || '999999');
                        const bDuration = parseFloat(b.dataset.duration || '999999');
                        return aDuration - bDuration;
                    }
                    default:
                        return 0;
                }
            });
        }

        // Update the display
        rideList.innerHTML = '';
        filteredRides.forEach(ride => {
            const clonedRide = ride.cloneNode(true);
            rideList.appendChild(clonedRide);
        });

        // Update counts if no rides match the filter
        if (filteredRides.length === 0) {
            const noResults = document.createElement('div');
            noResults.style.cssText = 'text-align: center; padding: 48px 0; color: var(--rx-text-muted);';
            noResults.innerHTML = `
                <i class="fa-solid fa-magnifying-glass" style="font-size: 2.5rem; margin-bottom: 12px; display: block; opacity: 0.6;"></i>
                <p style="font-size: 1.1rem; font-weight: 700; color: var(--rx-text-primary); margin: 0 0 6px;">No rides match your filters</p>
                <p style="font-size: 0.84rem; margin: 0;">Try adjusting or clearing your filters to see more results.</p>
            `;
            rideList.appendChild(noResults);
        }
    }

    // Add event listeners
    sortInputs.forEach(input => input.addEventListener('change', filterRides));
    timeInputs.forEach(input => input.addEventListener('change', filterRides));
    amenityInputs.forEach(input => input.addEventListener('change', filterRides));

    // Clear all filters
    if (clearFiltersBtn) {
        clearFiltersBtn.addEventListener('click', () => {
            sortInputs.forEach(input => input.checked = false);
            timeInputs.forEach(input => input.checked = false);
            amenityInputs.forEach(input => input.checked = false);
            
            rideList.innerHTML = '';
            allRides.forEach(ride => {
                const clonedRide = ride.cloneNode(true);
                rideList.appendChild(clonedRide);
            });
        });
    }

    // Initial filter call to set up the initial state
    filterRides();
});