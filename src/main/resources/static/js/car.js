document.addEventListener("DOMContentLoaded", function () {
  const carMakeSelect = document.getElementById("carMake");
  const carModelSelect = document.getElementById("carModel");

  // Fetch CSV and initialize searchable dropdowns
  fetch("/data/car_makes_models.csv")
    .then((response) => {
      if (!response.ok) throw new Error("Failed to load car data");
      return response.text();
    })
    .then((csvText) => {
      const carData = parseCSV(csvText);
      const makeList = Array.from(carData.keys()).sort();

      const makeDropdown = createSearchableDropdown(
        carMakeSelect,
        makeList,
        "Search make...",
        false
      );
      const modelDropdown = createSearchableDropdown(
        carModelSelect,
        [],
        "Search or type model...",
        true // allow custom input for models
      );

      // When make is selected, populate model dropdown
      carMakeSelect.addEventListener("change", function () {
        const models = carData.get(this.value) || [];
        modelDropdown.updateOptions(models);
        modelDropdown.clearSelection();
      });

      // Pre-select values for edit forms
      const preSelectedMake = carMakeSelect.dataset.selectedValue || "";
      if (preSelectedMake) {
        makeDropdown.setSelected(preSelectedMake);
        const models = carData.get(preSelectedMake) || [];
        modelDropdown.updateOptions(models);

        const preSelectedModel = carModelSelect.dataset.selectedValue || "";
        if (preSelectedModel) {
          modelDropdown.setSelected(preSelectedModel);
        }
      }
    })
    .catch((err) => {
      console.error("Error loading car makes/models:", err);
    });

  /**
   * Parses the CSV text into a Map<string, string[]>.
   * Makes with only placeholder models are included with an empty array.
   */
  function parseCSV(csvText) {
    const lines = csvText.split(/\r?\n/);
    const carMap = new Map();

    for (let i = 1; i < lines.length; i++) {
      const line = lines[i].trim();
      if (!line) continue;

      const commaIndex = line.indexOf(",");
      if (commaIndex === -1) continue;

      const make = line.substring(0, commaIndex).trim();
      const model = line.substring(commaIndex + 1).trim();

      if (!make) continue;

      if (!carMap.has(make)) {
        carMap.set(make, []);
      }

      if (model && !/^\.+$/.test(model)) {
        carMap.get(make).push(model);
      }
    }

    return carMap;
  }

  /**
   * Wraps a native <select> with a searchable input + dropdown.
   * @param {HTMLSelectElement} selectEl - The hidden select for form submission
   * @param {string[]} options - Initial list of options
   * @param {string} placeholder - Placeholder text
   * @param {boolean} allowCustom - If true, user can type a value not in the list
   */
  function createSearchableDropdown(selectEl, options, placeholder, allowCustom) {
    // Hide the native select but keep it in the form
    selectEl.style.position = "absolute";
    selectEl.style.opacity = "0";
    selectEl.style.pointerEvents = "none";
    selectEl.style.height = "0";
    selectEl.style.overflow = "hidden";
    selectEl.tabIndex = -1;

    // Create wrapper
    const wrapper = document.createElement("div");
    wrapper.className = "rx-searchable-dropdown";
    selectEl.parentNode.insertBefore(wrapper, selectEl);

    // Create search input
    const input = document.createElement("input");
    input.type = "text";
    input.className = "rx-form-input rx-search-input";
    input.placeholder = placeholder;
    input.autocomplete = "off";
    wrapper.appendChild(input);

    // Create clear button
    const clearBtn = document.createElement("span");
    clearBtn.className = "rx-search-clear";
    clearBtn.innerHTML = "&times;";
    clearBtn.style.display = "none";
    wrapper.appendChild(clearBtn);

    // Create dropdown list
    const dropdown = document.createElement("div");
    dropdown.className = "rx-search-dropdown";
    wrapper.appendChild(dropdown);

    let currentOptions = [...options];
    let activeIndex = -1;
    let isOpen = false;

    function renderOptions(filter) {
      dropdown.innerHTML = "";
      activeIndex = -1;
      const query = (filter || "").toLowerCase();

      const filtered = query
        ? currentOptions.filter((opt) => opt.toLowerCase().includes(query))
        : currentOptions;

      if (filtered.length === 0) {
        if (allowCustom && query) {
          // Show "use custom value" option
          const customItem = document.createElement("div");
          customItem.className = "rx-search-item rx-search-custom";
          customItem.dataset.value = filter;
          customItem.innerHTML =
            'Use "<strong>' + escapeHtml(filter) + '</strong>"';
          customItem.addEventListener("mousedown", (e) => {
            e.preventDefault();
            selectOption(filter);
          });
          dropdown.appendChild(customItem);
        } else {
          const noResult = document.createElement("div");
          noResult.className = "rx-search-item rx-search-no-result";
          noResult.textContent = query
            ? "No matches found"
            : "No options available";
          dropdown.appendChild(noResult);
        }
        return;
      }

      // If allowCustom and query doesn't exactly match any option, add custom option at top
      if (
        allowCustom &&
        query &&
        !filtered.some((opt) => opt.toLowerCase() === query)
      ) {
        const customItem = document.createElement("div");
        customItem.className = "rx-search-item rx-search-custom";
        customItem.dataset.value = filter;
        customItem.innerHTML =
          'Use "<strong>' + escapeHtml(filter) + '</strong>"';
        customItem.addEventListener("mousedown", (e) => {
          e.preventDefault();
          selectOption(filter);
        });
        dropdown.appendChild(customItem);
      }

      filtered.forEach((opt) => {
        const item = document.createElement("div");
        item.className = "rx-search-item";
        item.dataset.value = opt;

        if (query) {
          const matchIdx = opt.toLowerCase().indexOf(query);
          item.innerHTML =
            escapeHtml(opt.substring(0, matchIdx)) +
            '<mark class="rx-search-highlight">' +
            escapeHtml(opt.substring(matchIdx, matchIdx + query.length)) +
            "</mark>" +
            escapeHtml(opt.substring(matchIdx + query.length));
        } else {
          item.textContent = opt;
        }

        item.addEventListener("mousedown", (e) => {
          e.preventDefault();
          selectOption(opt);
        });

        dropdown.appendChild(item);
      });
    }

    function selectOption(value) {
      input.value = value;
      clearBtn.style.display = "inline-flex";
      closeDropdown();
      syncSelect(value);
    }

    function syncSelect(value) {
      let option = Array.from(selectEl.options).find(
        (opt) => opt.value === value
      );
      if (!option) {
        option = document.createElement("option");
        option.value = value;
        option.textContent = value;
        selectEl.appendChild(option);
      }
      selectEl.value = value;
      selectEl.dispatchEvent(new Event("change", { bubbles: true }));
    }

    function openDropdown() {
      if (isOpen) return;
      isOpen = true;
      renderOptions(input.value);
      dropdown.classList.add("rx-search-dropdown--open");
    }

    function closeDropdown() {
      isOpen = false;
      dropdown.classList.remove("rx-search-dropdown--open");
      activeIndex = -1;
    }

    function setActiveItem(idx) {
      const items = dropdown.querySelectorAll(
        ".rx-search-item:not(.rx-search-no-result)"
      );
      items.forEach((item) => item.classList.remove("rx-search-item--active"));
      if (idx >= 0 && idx < items.length) {
        activeIndex = idx;
        items[idx].classList.add("rx-search-item--active");
        items[idx].scrollIntoView({ block: "nearest" });
      }
    }

    // --- Event listeners ---
    input.addEventListener("focus", () => {
      openDropdown();
    });

    input.addEventListener("input", () => {
      if (!isOpen) openDropdown();
      renderOptions(input.value);
    });

    input.addEventListener("blur", () => {
      setTimeout(() => {
        closeDropdown();
        // For allowCustom fields: if user typed something but didn't pick, accept it
        if (allowCustom && input.value.trim() && selectEl.value !== input.value.trim()) {
          syncSelect(input.value.trim());
          clearBtn.style.display = "inline-flex";
        }
      }, 180);
    });

    input.addEventListener("keydown", (e) => {
      const items = dropdown.querySelectorAll(
        ".rx-search-item:not(.rx-search-no-result)"
      );
      if (e.key === "ArrowDown") {
        e.preventDefault();
        if (!isOpen) openDropdown();
        setActiveItem(Math.min(activeIndex + 1, items.length - 1));
      } else if (e.key === "ArrowUp") {
        e.preventDefault();
        setActiveItem(Math.max(activeIndex - 1, 0));
      } else if (e.key === "Enter") {
        e.preventDefault();
        if (activeIndex >= 0 && activeIndex < items.length) {
          selectOption(items[activeIndex].dataset.value);
        } else if (allowCustom && input.value.trim()) {
          selectOption(input.value.trim());
        }
      } else if (e.key === "Escape") {
        closeDropdown();
        input.blur();
      }
    });

    clearBtn.addEventListener("click", () => {
      input.value = "";
      clearBtn.style.display = "none";
      selectEl.value = "";
      selectEl.dispatchEvent(new Event("change", { bubbles: true }));
      input.focus();
    });

    // --- Public API ---
    function clearSelection() {
      input.value = "";
      clearBtn.style.display = "none";
      selectEl.value = "";
    }

    function updateOptions(newOptions) {
      currentOptions = [...newOptions];
      // Re-render if already open
      if (isOpen) {
        renderOptions(input.value);
      }
    }

    function setSelected(value) {
      input.value = value;
      clearBtn.style.display = value ? "inline-flex" : "none";
      syncSelect(value);
    }

    function escapeHtml(str) {
      const div = document.createElement("div");
      div.textContent = str;
      return div.innerHTML;
    }

    return { updateOptions, clearSelection, setSelected };
  }
});
