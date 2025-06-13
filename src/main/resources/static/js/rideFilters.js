document.addEventListener('DOMContentLoaded', function() {
    const rideList = document.getElementById('rideList');
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
            const rideElement = ride.querySelector('div');
            
            // Filter by time
            if (selectedTimes.length > 0) {
                const rideTime = rideElement.querySelector('span[th\\:text*="HH:mm"]').textContent.trim();
                const timeMatch = selectedTimes.some(timeRange => {
                    const [start, end] = timeRange.split('-');
                    return rideTime >= start && rideTime <= end;
                });
                if (!timeMatch) return false;
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
                const aElement = a.querySelector('div');
                const bElement = b.querySelector('div');

                switch (selectedSort) {
                    case 'earliest':
                        const aTime = aElement.querySelector('span[th\\:text*="HH:mm"]').textContent.trim();
                        const bTime = bElement.querySelector('span[th\\:text*="HH:mm"]').textContent.trim();
                        return aTime.localeCompare(bTime);
                    
                    case 'lowest':
                        const aPrice = parseFloat(aElement.querySelector('.text-green-400').textContent.replace('₹', '').trim());
                        const bPrice = parseFloat(bElement.querySelector('.text-green-400').textContent.replace('₹', '').trim());
                        return aPrice - bPrice;
                    
                    case 'close-departure':
                        // This would require coordinates to be implemented
                        return 0;
                    
                    case 'close-arrival':
                        // This would require coordinates to be implemented
                        return 0;
                    
                    case 'shortest':
                        // This would require duration to be implemented
                        return 0;
                    
                    default:
                        return 0;
                }
            });
        }

        // Update the display
        rideList.innerHTML = '';
        filteredRides.forEach(ride => {
            // Clone the ride element to avoid any event listener issues
            const clonedRide = ride.cloneNode(true);
            rideList.appendChild(clonedRide);
        });

        // Update counts if no rides match the filter
        if (filteredRides.length === 0) {
            const noResults = document.createElement('div');
            noResults.className = 'text-center py-8 text-gray-400';
            noResults.innerHTML = `
                <i class="fas fa-search text-4xl mb-4"></i>
                <p class="text-xl">No rides match your filters</p>
                <p class="text-sm mt-2">Try adjusting your filters to see more results</p>
            `;
            rideList.appendChild(noResults);
        }
    }

    // Add event listeners
    sortInputs.forEach(input => {
        input.addEventListener('change', filterRides);
    });

    timeInputs.forEach(input => {
        input.addEventListener('change', filterRides);
    });

    amenityInputs.forEach(input => {
        input.addEventListener('change', filterRides);
    });

    // Clear all filters
    clearFiltersBtn.addEventListener('click', () => {
        sortInputs.forEach(input => input.checked = false);
        timeInputs.forEach(input => input.checked = false);
        amenityInputs.forEach(input => input.checked = false);
        
        // Reset the display
        rideList.innerHTML = '';
        allRides.forEach(ride => {
            const clonedRide = ride.cloneNode(true);
            rideList.appendChild(clonedRide);
        });
    });

    // Initial filter call to set up the initial state
    filterRides();
}); 