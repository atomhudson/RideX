function setCurrentDateTime() {
    const now = new Date();
    // Indian Standard Time (UTC+5:30) offset adjustment
    now.setMinutes(now.getMinutes() + now.getTimezoneOffset() + 330);
    const istISOTime = now.toISOString().slice(0, 16);
    const startTimeInput = document.getElementById("departure_time") || document.getElementById("startTime");
    const endTimeInput = document.getElementById("endTime");
    if (startTimeInput) {
        startTimeInput.min = istISOTime;
        if (!startTimeInput.value) {
            startTimeInput.value = istISOTime;
        }
    }
    if (endTimeInput) {
        endTimeInput.min = istISOTime;
        if (!endTimeInput.value) {
            endTimeInput.value = istISOTime;
        }
    }
}
window.addEventListener("DOMContentLoaded", setCurrentDateTime);

function adjustSeat(change) {
    const seatValueEl = document.getElementById('seatValue');
    const seatInput = document.getElementById('seat');
    if (!seatValueEl || !seatInput) return;
    let current = parseInt(seatValueEl.textContent, 10) || 1;
    current = Math.min(10, Math.max(1, current + change));
    seatValueEl.textContent = current;
    seatInput.value = current;
    // Recalculate suggested price whenever seats change
    if (typeof window.tryDrawRoute === 'function') window.tryDrawRoute();
}

