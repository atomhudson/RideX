const yearSelect = document.getElementById("year");
const currentYear = new Date().getFullYear();
const startYear = 1980;

for (let y = currentYear; y >= startYear; y--) {
    const option = document.createElement("option");
    option.value = y;
    option.textContent = y;
    yearSelect.appendChild(option);
}
const fileInput = document.getElementById("car_image_url");
const previewContainer = document.getElementById("preview_container");

fileInput.addEventListener("change", function (event) {
    const files = Array.from(event.target.files);
    previewContainer.innerHTML = "";

    if (files.length > 3) {
        alert("You can only upload up to 3 images.");
        fileInput.value = ""; // Reset selection
        return;
    }

    files.forEach((file, index) => {
        const reader = new FileReader();
        reader.onload = function () {
            const imgWrapper = document.createElement("div");
            imgWrapper.className = "relative w-32 h-32";

            const img = document.createElement("img");
            img.src = reader.result;
            img.className = "object-cover w-full h-full rounded shadow";

            const removeBtn = document.createElement("button");
            removeBtn.textContent = "×";
            removeBtn.className = "absolute top-0 right-0 bg-red-600 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center cursor-pointer";
            removeBtn.onclick = function () {
                files.splice(index, 1); // Remove file from array
                const dataTransfer = new DataTransfer();
                files.forEach((f) => dataTransfer.items.add(f));
                fileInput.files = dataTransfer.files;
                fileInput.dispatchEvent(new Event("change")); // Re-trigger change
            };

            imgWrapper.appendChild(img);
            imgWrapper.appendChild(removeBtn);
            previewContainer.appendChild(imgWrapper);
        };
        reader.readAsDataURL(file);
    });
});
