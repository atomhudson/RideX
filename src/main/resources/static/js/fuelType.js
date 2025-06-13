const fuelTypes = [
  "Petrol",
  "Diesel",
  "CNG",
  "LPG",
  "Electric",
  "Hybrid (Petrol + Electric)",
  "Hybrid (Diesel + Electric)",
  "Plug-in Hybrid (PHEV)",
  "Hydrogen Fuel Cell",
  "Flex Fuel",
  "Bio-Diesel",
  "Compressed Hydrogen",
  "Ethanol",
  "Methanol",
  "Propane",
  "Solar-Powered",
  "Ultra Low Sulfur Diesel (ULSD)",
  "Liquefied Natural Gas (LNG)",
  "Synthetic Fuel (e-Fuel)",
  "Algae Biofuel",
  "Dimethyl Ether (DME)",
  "Hydrogen Internal Combustion"
];
const fuelTypeSelect = document.getElementById("fuelType");
fuelTypes.forEach((fuel) => {
  const option = document.createElement("option");
  option.value = fuel;
  option.textContent = fuel;
  fuelTypeSelect.appendChild(option);
});
