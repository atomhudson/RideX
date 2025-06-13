import cities from './cities.js';
import occupationSchema from './occupation.js';
import spokenLanguages from './spokenLanguages.js';

console.log("Details.js");

// Populate City Dropdown
const citySelect = document.createElement('select');
citySelect.id = 'city';
citySelect.name = 'city';
citySelect.className = 'w-full bg-gray-700 text-white border border-gray-600 rounded py-3 px-4';
cities.forEach(city => {
    const option = document.createElement('option');
    option.value = city.name+", "+city.state;
    option.textContent = `${city.name}, ${city.state}`;
    citySelect.appendChild(option);
});
document.getElementById('city').replaceWith(citySelect);

// Occupation Autocomplete
const occupationInput = document.getElementById('occupation');
occupationInput.setAttribute('list', 'occupationList');
const datalist = document.createElement('datalist');
datalist.id = 'occupationList';
occupationSchema.properties.occupation.enum.forEach(item => {
    const option = document.createElement('option');
    option.value = item;
    datalist.appendChild(option);
});
document.body.appendChild(datalist);

// Spoken Languages Autocomplete
const langInput = document.getElementById('spokenLanguages');
langInput.setAttribute('list', 'languageList');
const langList = document.createElement('datalist');
langList.id = 'languageList';
spokenLanguages.properties.languages.items.enum.forEach(lang => {
    const option = document.createElement('option');
    option.value = lang;
    langList.appendChild(option);
});
document.body.appendChild(langList);