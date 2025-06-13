import cities from './cities.js';

function populateCityDatalist(id) {
    const datalist = document.getElementById(id);

    cities.forEach(city => {
        const option = document.createElement("option");
        option.value = `${city.name}, ${city.state}`;
        datalist.appendChild(option);
    });
}

window.addEventListener("DOMContentLoaded", () => {
    populateCityDatalist("from-list");
    populateCityDatalist("to-list");
});
