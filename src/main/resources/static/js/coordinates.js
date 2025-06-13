async function geocode(fullAddress) {
    const url = `https://nominatim.openstreetmap.org/search?format=json&limit=1&q=${encodeURIComponent(fullAddress)}`;
    const response = await fetch(url);
    const data = await response.json();
    return data.length > 0 ? data[0] : null; // Ensure we return the first object
}

async function updateFromCoordinates() {
    const fullAddress = document.getElementById("fromAddress").value;
    const city = document.getElementById("from").value;
    const combinedAddress = `${fullAddress}, ${city}`;

    const result = await geocode(combinedAddress);
    if (result) {
        document.getElementById("fromLat").value = result.lat;
        document.getElementById("fromLon").value = result.lon;
    }
}

async function updateToCoordinates() {
    const fullAddress = document.getElementById("toAddress").value;
    const city = document.getElementById("to").value;
    const combinedAddress = `${fullAddress}, ${city}`;

    const result = await geocode(combinedAddress);
    if (result) {
        document.getElementById("toLat").value = result.lat;
        document.getElementById("toLon").value = result.lon;
    }
}

// Trigger geocoding when user finishes typing or before form submit
document.getElementById("fromAddress").addEventListener("blur", updateFromCoordinates);
document.getElementById("from").addEventListener("blur", updateFromCoordinates);

document.getElementById("toAddress").addEventListener("blur", updateToCoordinates);
document.getElementById("to").addEventListener("blur", updateToCoordinates);