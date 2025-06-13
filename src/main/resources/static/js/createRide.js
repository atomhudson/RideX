function setCurrentDateTime() {
    const now = new Date();
    now.setMinutes(now.getMinutes() + now.getTimezoneOffset() + 330);
    const istISOTime = now.toISOString().slice(0, 16);
    const startTimeInput = document.getElementById("startTime");
    const endTimeInput = document.getElementById("endTime");
    if (startTimeInput) {
        startTimeInput.min = istISOTime;
        startTimeInput.value = istISOTime;
    }
    if (endTimeInput) {
        endTimeInput.min = istISOTime;
        endTimeInput.value = istISOTime;
    }
}
window.addEventListener("DOMContentLoaded", setCurrentDateTime);
function adjustSeat(change) {
    const seatValueEl = document.getElementById('seatValue');
    const seatInput = document.getElementById('seat');
    let current = parseInt(seatValueEl.textContent, 10);
    current = Math.min(10, Math.max(1, current + change));
    seatValueEl.textContent = current;
    seatInput.value = current;
}
