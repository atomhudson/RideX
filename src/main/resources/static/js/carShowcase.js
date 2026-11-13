/**
 * RideX — Spring '26 Edition Interactive 3D Showcase & Animations
 * Inspired by Shopify Editions, Linear & Apple Keynote web showcases.
 */

(function () {
    'use strict';

    // State
    // State
    const state = {
        mode: 'eco', // eco, cruise, boost
        headlights: true,
        soundEnabled: true,
        speed: 84,
        rpm: 3200,
        distance: 40,
        inLaneChange: false,
        pickedUpCount: 3
    };

    // Synthesized Web Audio Tactile Effects (Shopify / Apple Keynote style)
    let audioCtx = null;
    function getAudioContext() {
        if (!audioCtx && (window.AudioContext || window.webkitAudioContext)) {
            const AudioCtxClass = window.AudioContext || window.webkitAudioContext;
            audioCtx = new AudioCtxClass();
        }
        if (audioCtx && audioCtx.state === 'suspended') {
            audioCtx.resume();
        }
        return audioCtx;
    }

    function playSound(type) {
        if (!state.soundEnabled) return;
        try {
            const ctx = getAudioContext();
            if (!ctx) return;
            const now = ctx.currentTime;

            if (type === 'click') {
                const osc = ctx.createOscillator();
                const gain = ctx.createGain();
                osc.type = 'sine';
                osc.frequency.setValueAtTime(800, now);
                osc.frequency.exponentialRampToValueAtTime(350, now + 0.04);
                gain.gain.setValueAtTime(0.04, now);
                gain.gain.exponentialRampToValueAtTime(0.001, now + 0.04);
                osc.connect(gain);
                gain.connect(ctx.destination);
                osc.start(now);
                osc.stop(now + 0.04);
            } else if (type === 'mode') {
                const osc = ctx.createOscillator();
                const gain = ctx.createGain();
                osc.type = 'triangle';
                osc.frequency.setValueAtTime(520, now);
                osc.frequency.exponentialRampToValueAtTime(1040, now + 0.08);
                gain.gain.setValueAtTime(0.05, now);
                gain.gain.exponentialRampToValueAtTime(0.001, now + 0.08);
                osc.connect(gain);
                gain.connect(ctx.destination);
                osc.start(now);
                osc.stop(now + 0.08);
            } else if (type === 'nitro') {
                const osc = ctx.createOscillator();
                const gain = ctx.createGain();
                osc.type = 'sawtooth';
                osc.frequency.setValueAtTime(220, now);
                osc.frequency.exponentialRampToValueAtTime(740, now + 0.22);
                gain.gain.setValueAtTime(0.04, now);
                gain.gain.exponentialRampToValueAtTime(0.001, now + 0.22);
                osc.connect(gain);
                gain.connect(ctx.destination);
                osc.start(now);
                osc.stop(now + 0.22);
            } else if (type === 'pickup') {
                const notes = [523.25, 659.25, 783.99]; // C5, E5, G5 chord
                notes.forEach((freq, idx) => {
                    const osc = ctx.createOscillator();
                    const gain = ctx.createGain();
                    osc.type = 'sine';
                    osc.frequency.setValueAtTime(freq, now + idx * 0.05);
                    gain.gain.setValueAtTime(0.04, now + idx * 0.05);
                    gain.gain.exponentialRampToValueAtTime(0.001, now + idx * 0.05 + 0.12);
                    osc.connect(gain);
                    gain.connect(ctx.destination);
                    osc.start(now + idx * 0.05);
                    osc.stop(now + idx * 0.05 + 0.13);
                });
            }
        } catch (e) {
            // Audio context not allowed until gesture
        }
    }

    // Initialize all components once DOM is ready
    document.addEventListener('DOMContentLoaded', () => {
        init3DCarHero();
        initSavingsCalculator();
        initHighwaySimulator();
        initQuickSearch();
        initKeyboardShortcuts();
    });

    /* ==========================================================================
       1. 3D CAR HERO INTERACTIVE STAGE & PARALLAX
       ========================================================================== */
    function init3DCarHero() {
        const stage = document.getElementById('carStage');
        const carImg = document.getElementById('carHeroImg');
        const beams = document.getElementById('carBeams');
        const speedVal = document.getElementById('hudSpeed');
        const modePills = document.querySelectorAll('.rx-hud-mode-btn');
        const headlightToggle = document.getElementById('headlightToggle');
        const soundToggle = document.getElementById('soundToggle');

        if (!stage || !carImg) return;

        // Sound Toggle
        if (soundToggle) {
            soundToggle.addEventListener('click', () => {
                state.soundEnabled = !state.soundEnabled;
                soundToggle.classList.toggle('active', state.soundEnabled);
                const icon = soundToggle.querySelector('i');
                if (icon) {
                    icon.className = state.soundEnabled ? 'fa-solid fa-volume-high' : 'fa-solid fa-volume-xmark';
                    icon.style.color = state.soundEnabled ? '#10b981' : 'var(--rx-text-muted)';
                }
                if (state.soundEnabled) playSound('click');
            });
        }

        // 3D Mouse Parallax & Dynamic Specular Tilt
        stage.addEventListener('mousemove', (e) => {
            const rect = stage.getBoundingClientRect();
            const x = (e.clientX - rect.left) / rect.width - 0.5;
            const y = (e.clientY - rect.top) / rect.height - 0.5;

            // Subtle 3D tilt
            const rotY = x * 14; // max 7 deg
            const rotX = -y * 10; // max 5 deg
            const transZ = 20;

            carImg.style.transform = `perspective(1000px) rotateY(${rotY}deg) rotateX(${rotX}deg) translateZ(${transZ}px)`;

            // Update beam tilt if headlights active
            if (beams) {
                beams.style.transform = `perspective(1000px) rotateY(${rotY * 0.8}deg) rotateX(${rotX * 0.8}deg)`;
            }

            // Stage ambient lighting follows cursor
            stage.style.setProperty('--light-x', `${(x + 0.5) * 100}%`);
            stage.style.setProperty('--light-y', `${(y + 0.5) * 100}%`);
        });

        stage.addEventListener('mouseleave', () => {
            carImg.style.transform = 'perspective(1000px) rotateY(0deg) rotateX(0deg) translateZ(0px)';
            if (beams) beams.style.transform = 'perspective(1000px) rotateY(0deg) rotateX(0deg)';
        });

        // Headlight Toggle
        if (headlightToggle) {
            headlightToggle.addEventListener('click', () => {
                state.headlights = !state.headlights;
                playSound('click');
                if (beams) {
                    beams.classList.toggle('rx-beams-off', !state.headlights);
                }
                headlightToggle.classList.toggle('active', state.headlights);
                const icon = headlightToggle.querySelector('i');
                if (icon) {
                    icon.style.color = state.headlights ? '#10b981' : 'var(--rx-text-muted)';
                }
            });
        }

        // Mode Selector: Eco, Cruise, Boost
        modePills.forEach(pill => {
            pill.addEventListener('click', () => {
                modePills.forEach(p => p.classList.remove('active'));
                pill.classList.add('active');
                playSound('mode');

                const mode = pill.getAttribute('data-mode') || 'eco';
                state.mode = mode;

                // Configure speed, highway speed & HUD glow
                let targetSpeed = 84;
                let colorClass = 'mode-eco';

                if (mode === 'eco') {
                    targetSpeed = 78;
                    colorClass = 'mode-eco';
                    document.documentElement.style.setProperty('--rx-car-aura', 'rgba(16, 185, 129, 0.35)');
                } else if (mode === 'cruise') {
                    targetSpeed = 108;
                    colorClass = 'mode-cruise';
                    document.documentElement.style.setProperty('--rx-car-aura', 'rgba(6, 182, 212, 0.4)');
                } else if (mode === 'boost') {
                    targetSpeed = 145;
                    colorClass = 'mode-boost';
                    document.documentElement.style.setProperty('--rx-car-aura', 'rgba(139, 92, 246, 0.45)');
                }

                // Animate speedometer
                animateNumber(speedVal, state.speed, targetSpeed, 600);
                state.speed = targetSpeed;

                // Update road animation duration
                const highwayLines = document.querySelector('.rx-highway-stripes');
                if (highwayLines) {
                    highwayLines.style.animationDuration = mode === 'boost' ? '0.25s' : mode === 'cruise' ? '0.4s' : '0.6s';
                }
            });
        });
    }

    /* ==========================================================================
       2. INTERACTIVE COMMUTE SAVINGS & CO2 CALCULATOR (BENTO)
       ========================================================================== */
    function initSavingsCalculator() {
        const slider = document.getElementById('savingsSlider');
        const distDisplay = document.getElementById('calcDistanceVal');
        const moneySaved = document.getElementById('calcMoneyVal');
        const fuelSaved = document.getElementById('calcFuelVal');
        const co2Saved = document.getElementById('calcCo2Val');
        const co2Circle = document.getElementById('co2ProgressCircle');

        if (!slider) return;

        function update() {
            const km = parseInt(slider.value, 10);
            state.distance = km;

            if (distDisplay) distDisplay.textContent = `${km} km`;

            // Calculations based on Indian commute averages:
            // Single driver costs ~ ₹8/km in fuel & maintenance
            // Carpooling shares ~ 60% of the cost across 3 passengers
            const monthlyDays = 22;
            const soloCost = km * 2 * monthlyDays * 7.5;
            const carpoolShare = soloCost * 0.62;
            const liters = Math.round((km * 2 * monthlyDays) / 14); // 14 km/L avg
            const co2Kg = Math.round(liters * 2.31); // 2.31kg CO2 per liter petrol

            if (moneySaved) moneySaved.textContent = `₹${Math.round(carpoolShare).toLocaleString('en-IN')}`;
            if (fuelSaved) fuelSaved.textContent = `${liters} L`;
            if (co2Saved) co2Saved.textContent = `${co2Kg} kg`;

            // Update circular progress dial (circumference = 282.7)
            if (co2Circle) {
                const maxKg = 450;
                const ratio = Math.min(co2Kg / maxKg, 1);
                const offset = 282.7 * (1 - ratio);
                co2Circle.style.strokeDashoffset = offset;
            }
        }

        slider.addEventListener('input', update);
        update(); // run initially
    }

    /* ==========================================================================
       3. HIGHWAY LIVE CAR SIMULATOR & INTERACTIVE PASSENGER PICKUP
       ========================================================================== */
    function initHighwaySimulator() {
        const canvas = document.getElementById('highwaySimCanvas');
        const accelBtn = document.getElementById('simAccelBtn');
        const laneBtn = document.getElementById('simLaneBtn');
        const pickupBtn = document.getElementById('simPickupBtn');
        const simSpeedText = document.getElementById('simSpeedDisplay');
        const simStatusText = document.getElementById('simStatusDisplay');

        if (!canvas) return;

        const ctx = canvas.getContext('2d');
        let width = canvas.width = canvas.parentElement.clientWidth || 800;
        let height = canvas.height = 240;

        window.addEventListener('resize', () => {
            if (canvas && canvas.parentElement) {
                width = canvas.width = canvas.parentElement.clientWidth;
                height = canvas.height = 240;
            }
        });

        // Simulation parameters
        let roadOffset = 0;
        let currentSpeed = 70;
        let targetSpeed = 70;
        let carY = height * 0.55;
        let targetCarY = height * 0.55;
        let laneIndex = 1; // 0 = top, 1 = mid, 2 = bottom (pickup bay)
        let particles = [];
        let waypoints = [
            { x: width * 0.85, name: 'Gurugram Cyber City', passed: false }
        ];
        let pickupActive = false;
        let pickupTimer = 0;

        // Populate speed particles
        for (let i = 0; i < 35; i++) {
            particles.push({
                x: Math.random() * width,
                y: 40 + Math.random() * (height - 80),
                length: 15 + Math.random() * 30,
                speed: 1.5 + Math.random() * 2
            });
        }

        function render() {
            ctx.clearRect(0, 0, width, height);

            // 1. Asphalt Background
            const bgGrad = ctx.createLinearGradient(0, 0, 0, height);
            bgGrad.addColorStop(0, '#f1f5f9');
            bgGrad.addColorStop(0.15, '#e2e8f0');
            bgGrad.addColorStop(0.85, '#e2e8f0');
            bgGrad.addColorStop(1, '#f1f5f9');
            ctx.fillStyle = bgGrad;
            ctx.fillRect(0, 30, width, height - 60);

            // Guard rails
            ctx.fillStyle = '#cbd5e1';
            ctx.fillRect(0, 28, width, 4);
            ctx.fillRect(0, height - 32, width, 4);

            // 2. Animated Lane Markers
            roadOffset -= currentSpeed * 0.15;
            if (roadOffset <= -60) roadOffset = 0;

            ctx.lineWidth = 3;
            ctx.strokeStyle = '#ffffff';
            ctx.setLineDash([28, 24]);
            ctx.lineDashOffset = roadOffset;

            // 2 lane divider lines
            ctx.beginPath();
            ctx.moveTo(0, height * 0.38);
            ctx.lineTo(width, height * 0.38);
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(0, height * 0.65);
            ctx.lineTo(width, height * 0.65);
            ctx.stroke();

            ctx.setLineDash([]); // Reset line dash

            // 3. Speed Particles (Aerodynamic wind lines)
            ctx.strokeStyle = 'rgba(16, 185, 129, 0.4)';
            ctx.lineWidth = 1.5;
            particles.forEach(p => {
                p.x -= (currentSpeed * 0.08) * p.speed;
                if (p.x < -p.length) p.x = width + Math.random() * 50;

                ctx.beginPath();
                ctx.moveTo(p.x, p.y);
                ctx.lineTo(p.x + p.length, p.y);
                ctx.stroke();
            });

            // 4. Smooth Car Motion
            currentSpeed += (targetSpeed - currentSpeed) * 0.08;
            carY += (targetCarY - carY) * 0.1;

            const carX = 140;

            // Headlight illumination beam
            const beamGrad = ctx.createRadialGradient(carX + 60, carY, 10, carX + 220, carY, 150);
            beamGrad.addColorStop(0, 'rgba(16, 185, 129, 0.35)');
            beamGrad.addColorStop(0.7, 'rgba(16, 185, 129, 0.08)');
            beamGrad.addColorStop(1, 'transparent');

            ctx.fillStyle = beamGrad;
            ctx.beginPath();
            ctx.moveTo(carX + 45, carY - 14);
            ctx.lineTo(carX + 260, carY - 60);
            ctx.lineTo(carX + 260, carY + 60);
            ctx.lineTo(carX + 45, carY + 14);
            ctx.closePath();
            ctx.fill();

            // Car Body Shadow
            ctx.fillStyle = 'rgba(15, 23, 42, 0.25)';
            ctx.beginPath();
            ctx.ellipse(carX + 2, carY + 16, 42, 10, 0, 0, Math.PI * 2);
            ctx.fill();

            // Car Body (Top-down luxury EV shape)
            ctx.save();
            ctx.translate(carX, carY);

            // Main chassis
            ctx.fillStyle = '#0f172a';
            roundRect(ctx, -38, -16, 76, 32, 12);
            ctx.fill();

            // Emerald cyber racing stripe / accents
            ctx.fillStyle = '#10b981';
            ctx.fillRect(-10, -15, 20, 2);
            ctx.fillRect(-10, 13, 20, 2);

            // Glass canopy / Panoramic roof
            ctx.fillStyle = '#38bdf8';
            ctx.globalAlpha = 0.7;
            roundRect(ctx, -14, -10, 32, 20, 6);
            ctx.fill();
            ctx.globalAlpha = 1.0;

            // Headlight LEDs (Green / White glowing dots)
            ctx.fillStyle = '#34d399';
            ctx.shadowColor = '#10b981';
            ctx.shadowBlur = 8;
            ctx.fillRect(34, -14, 5, 4);
            ctx.fillRect(34, 10, 5, 4);

            // Taillights
            ctx.fillStyle = '#ef4444';
            ctx.shadowColor = '#ef4444';
            ctx.shadowBlur = 6;
            ctx.fillRect(-38, -13, 3, 5);
            ctx.fillRect(-38, 8, 3, 5);
            ctx.shadowBlur = 0;

            ctx.restore();

            // 5. Pickup Passenger Overlay if active
            if (pickupActive) {
                pickupTimer++;
                // Draw pickup marker near the car
                ctx.fillStyle = '#10b981';
                ctx.beginPath();
                ctx.arc(carX + 110, carY - 26, 14, 0, Math.PI * 2);
                ctx.fill();

                ctx.fillStyle = '#ffffff';
                ctx.font = 'bold 12px sans-serif';
                ctx.textAlign = 'center';
                ctx.fillText('✓', carX + 110, carY - 22);

                ctx.fillStyle = '#0f172a';
                ctx.font = 'bold 11px sans-serif';
                ctx.fillText('+1 Rider Boarded (OTP Verified)', carX + 110, carY - 44);

                if (pickupTimer > 120) {
                    pickupActive = false;
                    targetCarY = height * 0.52;
                    targetSpeed = 85;
                    if (simStatusText) simStatusText.innerHTML = '<span style="color:#10b981;">● Cruising</span> · Expressway Mode';
                }
            }

            if (simSpeedText) simSpeedText.textContent = `${Math.round(currentSpeed)} km/h`;

            requestAnimationFrame(render);
        }

        requestAnimationFrame(render);

        // Controls
        if (accelBtn) {
            accelBtn.addEventListener('click', () => {
                playSound('nitro');
                targetSpeed = 125;
                if (simStatusText) simStatusText.innerHTML = '<span style="color:#06b6d4;">⚡ Nitro Surge</span> · High Velocity';
                setTimeout(() => { targetSpeed = 75; }, 2500);
            });
        }

        if (laneBtn) {
            laneBtn.addEventListener('click', () => {
                playSound('click');
                laneIndex = (laneIndex + 1) % 2;
                targetCarY = laneIndex === 0 ? height * 0.28 : height * 0.52;
                if (simStatusText) simStatusText.innerHTML = '<span style="color:#8b5cf6;">⇄ Lane Change</span> · Autonomous Assist';
            });
        }

        if (pickupBtn) {
            pickupBtn.addEventListener('click', () => {
                playSound('pickup');
                pickupActive = true;
                pickupTimer = 0;
                targetSpeed = 25;
                targetCarY = height * 0.72; // pull into pickup shoulder
                if (simStatusText) simStatusText.innerHTML = '<span style="color:#10b981;">📍 Station Pickup</span> · Authenticating OTP...';
            });
        }
    }

    /* Helper: Canvas Rounded Rect */
    function roundRect(ctx, x, y, width, height, radius) {
        ctx.beginPath();
        ctx.moveTo(x + radius, y);
        ctx.lineTo(x + width - radius, y);
        ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
        ctx.lineTo(x + width, y + height - radius);
        ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
        ctx.lineTo(x + radius, y + height);
        ctx.quadraticCurveTo(x, y + height, x, y + height - radius);
        ctx.lineTo(x, y + radius);
        ctx.quadraticCurveTo(x, y, x + radius, y);
        ctx.closePath();
    }

    /* ==========================================================================
       4. QUICK SEARCH HUD & AUTOFILL CHIPS
       ========================================================================== */
    function initQuickSearch() {
        const chips = document.querySelectorAll('.rx-quick-route-chip');
        const fromInput = document.getElementById('hudFromCity');
        const toInput = document.getElementById('hudToCity');

        chips.forEach(chip => {
            chip.addEventListener('click', () => {
                playSound('click');
                const from = chip.getAttribute('data-from');
                const to = chip.getAttribute('data-to');

                if (fromInput && from) fromInput.value = from;
                if (toInput && to) toInput.value = to;

                // Animate chip selection
                chips.forEach(c => c.classList.remove('selected'));
                chip.classList.add('selected');

                // Highlight submit button
                const searchBtn = document.getElementById('hudSearchSubmit');
                if (searchBtn) {
                    searchBtn.classList.add('rx-pulse-btn');
                    setTimeout(() => searchBtn.classList.remove('rx-pulse-btn'), 800);
                }
            });
        });
    }

    /* ==========================================================================
       5. KEYBOARD SHORTCUTS (Ctrl+K / ⌘K)
       ========================================================================== */
    function initKeyboardShortcuts() {
        window.addEventListener('keydown', (e) => {
            if ((e.ctrlKey || e.metaKey) && e.key === 'k') {
                e.preventDefault();
                const fromInput = document.getElementById('hudFromCity');
                if (fromInput) {
                    fromInput.focus();
                    fromInput.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }
        });
    }

    /* Number Animation utility */
    function animateNumber(element, start, end, duration) {
        if (!element) return;
        const startTime = performance.now();
        function update(now) {
            const progress = Math.min((now - startTime) / duration, 1);
            const value = Math.round(start + (end - start) * progress);
            element.textContent = value;
            if (progress < 1) requestAnimationFrame(update);
        }
        requestAnimationFrame(update);
    }

})();
