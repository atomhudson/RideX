/**
 * Razorpay Standard Checkout & Intelligent Sandbox Simulator for RideX
 */
(function() {
    // Dynamically load Razorpay SDK if not present
    function loadRazorpaySdk(callback) {
        if (window.Razorpay) {
            callback();
            return;
        }
        const script = document.createElement("script");
        script.src = "https://checkout.razorpay.com/v1/checkout.js";
        script.async = true;
        script.onload = callback;
        script.onerror = function() {
            console.warn("Could not load external Razorpay checkout.js script. Falling back to sandbox.");
            callback();
        };
        document.head.appendChild(script);
    }

    /**
     * Checks if current payment is running in mock/sandbox mode
     */
    function isMockMode(options) {
        if (!options.key || options.key.includes("RideXTestKey")) return true;
        if (options.order_id && options.order_id.startsWith("order_mock_")) return true;
        return false;
    }

    /**
     * Render the RideX Sandbox Payment Simulator Modal
     */
    function openSandboxSimulator(options) {
        let existing = document.getElementById("rxSandboxModal");
        if (existing) existing.remove();

        const amountFormatted = (options.amount / 100).toFixed(2);

        const modalHtml = `
        <div id="rxSandboxModal" style="position:fixed;inset:0;z-index:999999;background:rgba(0,0,0,0.82);backdrop-filter:blur(10px);display:flex;align-items:center;justify-content:center;padding:16px;animation:rxFadeIn 0.25s ease;">
            <div style="background:#131822;border:1px solid rgba(16,185,129,0.3);border-radius:24px;max-width:440px;width:100%;box-shadow:0 30px 80px rgba(0,0,0,0.85);overflow:hidden;color:#f3f4f6;font-family:system-ui,-apple-system,sans-serif;">
                <!-- Header -->
                <div style="background:linear-gradient(135deg, #064e3b 0%, #0f172a 100%);padding:20px 24px;border-bottom:1px solid rgba(255,255,255,0.08);display:flex;justify-content:space-between;align-items:center;">
                    <div>
                        <div style="display:flex;align-items:center;gap:8px;margin-bottom:4px;">
                            <span style="font-size:1.15rem;font-weight:800;color:#34d399;letter-spacing:-0.02em;">RideX Pay</span>
                            <span style="background:rgba(16,185,129,0.2);color:#34d399;border:1px solid rgba(16,185,129,0.4);font-size:0.65rem;padding:2px 8px;border-radius:999px;font-weight:700;text-transform:uppercase;">Sandbox Test Mode</span>
                        </div>
                        <p style="margin:0;font-size:0.75rem;color:#94a3b8;">${options.description || 'Ride Booking Payment'}</p>
                    </div>
                    <button id="rxCloseSandbox" style="background:transparent;border:none;color:#94a3b8;font-size:1.4rem;cursor:pointer;line-height:1;padding:4px;">&times;</button>
                </div>

                <!-- Body -->
                <div style="padding:24px;">
                    <!-- Amount display -->
                    <div style="text-align:center;margin-bottom:20px;background:rgba(255,255,255,0.03);border:1px solid rgba(255,255,255,0.06);border-radius:16px;padding:16px;">
                        <span style="font-size:0.75rem;color:#94a3b8;text-transform:uppercase;letter-spacing:0.05em;display:block;margin-bottom:4px;">Amount Payable</span>
                        <div style="font-size:2rem;font-weight:800;color:#fff;">₹${amountFormatted}</div>
                        <div style="font-size:0.7rem;color:#64748b;font-family:monospace;margin-top:4px;">Order: ${options.order_id}</div>
                    </div>

                    <!-- Test Methods -->
                    <div style="margin-bottom:20px;">
                        <label style="font-size:0.75rem;color:#94a3b8;font-weight:600;display:block;margin-bottom:8px;">SIMULATED PAYMENT METHOD</label>
                        <div style="display:grid;grid-template-columns:1fr 1fr 1fr;gap:8px;">
                            <div style="background:rgba(16,185,129,0.12);border:1px solid #10b981;padding:10px 8px;border-radius:12px;text-align:center;cursor:pointer;">
                                <div style="font-size:1rem;margin-bottom:2px;">⚡</div>
                                <span style="font-size:0.75rem;font-weight:700;color:#34d399;">UPI (Auto)</span>
                            </div>
                            <div style="background:rgba(255,255,255,0.03);border:1px solid rgba(255,255,255,0.08);padding:10px 8px;border-radius:12px;text-align:center;">
                                <div style="font-size:1rem;margin-bottom:2px;">💳</div>
                                <span style="font-size:0.75rem;font-weight:600;color:#94a3b8;">Test Card</span>
                            </div>
                            <div style="background:rgba(255,255,255,0.03);border:1px solid rgba(255,255,255,0.08);padding:10px 8px;border-radius:12px;text-align:center;">
                                <div style="font-size:1rem;margin-bottom:2px;">🏦</div>
                                <span style="font-size:0.75rem;font-weight:600;color:#94a3b8;">NetBanking</span>
                            </div>
                        </div>
                    </div>

                    <!-- Action buttons -->
                    <div style="display:flex;flex-direction:column;gap:10px;">
                        <button id="rxSimulateSuccess" style="width:100%;padding:14px;background:#10b981;border:none;border-radius:14px;color:#fff;font-weight:700;font-size:0.95rem;cursor:pointer;display:flex;align-items:center;justify-content:center;gap:8px;box-shadow:0 6px 20px rgba(16,185,129,0.35);transition:transform 0.15s ease;">
                            <span>✓</span> Simulate Successful Payment (₹${amountFormatted})
                        </button>
                        <button id="rxSimulateFailure" style="width:100%;padding:10px;background:transparent;border:1px solid rgba(239,68,68,0.4);border-radius:12px;color:#f87171;font-weight:600;font-size:0.8rem;cursor:pointer;">
                            Simulate Payment Failure / Cancel
                        </button>
                    </div>

                    <!-- Developer note -->
                    <div style="margin-top:16px;padding:10px 12px;background:rgba(59,130,246,0.08);border:1px solid rgba(59,130,246,0.2);border-radius:12px;font-size:0.72rem;color:#93c5fd;line-height:1.4;">
                        <strong>💡 Live Razorpay Test Keys:</strong> To use the official Razorpay popup, sign up for free at <a href="https://dashboard.razorpay.com/signup" target="_blank" style="color:#60a5fa;text-decoration:underline;">razorpay.com</a> (no KYC needed for test mode) and add your keys in <code style="background:rgba(0,0,0,0.3);padding:1px 4px;border-radius:4px;">application.properties</code>.
                    </div>
                </div>
            </div>
        </div>
        `;

        const wrapper = document.createElement("div");
        wrapper.innerHTML = modalHtml;
        document.body.appendChild(wrapper.firstElementChild);

        const modal = document.getElementById("rxSandboxModal");

        // Close handlers
        document.getElementById("rxCloseSandbox").onclick = function() {
            modal.remove();
            if (options.modal && typeof options.modal.ondismiss === 'function') {
                options.modal.ondismiss();
            }
        };

        document.getElementById("rxSimulateFailure").onclick = function() {
            modal.remove();
            if (options.onFailure && typeof options.onFailure === 'function') {
                options.onFailure({ error: { description: "Payment cancelled by user in Sandbox simulator." } });
            } else if (options.modal && typeof options.modal.ondismiss === 'function') {
                options.modal.ondismiss();
            }
        };

        // Simulate success
        document.getElementById("rxSimulateSuccess").onclick = function() {
            const btn = document.getElementById("rxSimulateSuccess");
            btn.innerHTML = 'Verifying Transaction...';
            btn.disabled = true;

            const randomSuffix = Math.random().toString(36).substring(2, 10).toUpperCase();
            const mockPaymentId = "pay_mock_" + randomSuffix;
            const mockSignature = "sig_mock_" + randomSuffix;

            setTimeout(function() {
                modal.remove();
                if (typeof options.handler === 'function') {
                    options.handler({
                        razorpay_order_id: options.order_id,
                        razorpay_payment_id: mockPaymentId,
                        razorpay_signature: mockSignature
                    });
                }
            }, 600);
        };
    }

    /**
     * Universal checkout open function: opens real Razorpay if configured with live/test key,
     * or seamlessly launches RideX Sandbox Simulator if using mock/test key.
     */
    window.openRideXCheckout = function(options) {
        if (isMockMode(options)) {
            console.log("RideX: Running in Sandbox Payment Simulator Mode.");
            openSandboxSimulator(options);
            return;
        }

        loadRazorpaySdk(function() {
            if (!window.Razorpay) {
                console.warn("Razorpay script unavailable, opening Sandbox mode.");
                openSandboxSimulator(options);
                return;
            }

            try {
                const rzp = new window.Razorpay(options);
                if (options.onFailure && typeof options.onFailure === 'function') {
                    rzp.on('payment.failed', options.onFailure);
                }
                rzp.open();
            } catch (err) {
                console.error("Razorpay initiation failed, falling back to sandbox simulator:", err);
                openSandboxSimulator(options);
            }
        });
    };

    /**
     * Function to pay for an existing booking (e.g. from My Trips)
     */
    window.payExistingRequest = function(requestId, amount, routeDesc) {
        (async function() {
            try {
                const res = await fetch('/api/payment/create-order', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ requestId: requestId })
                });

                const data = await res.json();
                if (!res.ok) {
                    alert(data.error || "Unable to initiate payment for this request.");
                    return;
                }

                if (data.zeroAmount) {
                    alert("This ride is fully covered! Status updated.");
                    window.location.reload();
                    return;
                }

                const options = {
                    key: data.keyId,
                    amount: data.amount,
                    currency: data.currency || "INR",
                    name: "RideX Carpooling",
                    description: routeDesc || "Ride Booking",
                    image: "/images/logo.svg",
                    order_id: data.orderId,
                    handler: async function(paymentRes) {
                        try {
                            const verifyRes = await fetch('/api/payment/verify', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/json' },
                                body: JSON.stringify({
                                    requestId: requestId,
                                    razorpay_order_id: paymentRes.razorpay_order_id,
                                    razorpay_payment_id: paymentRes.razorpay_payment_id,
                                    razorpay_signature: paymentRes.razorpay_signature
                                })
                            });
                            const verifyData = await verifyRes.json();
                            if (verifyRes.ok && verifyData.success) {
                                alert("Payment successful! Payment ID: " + paymentRes.razorpay_payment_id);
                                window.location.reload();
                            } else {
                                alert(verifyData.error || "Verification failed. Please contact support.");
                            }
                        } catch (err) {
                            console.error("Verification error:", err);
                            alert("Error verifying payment.");
                        }
                    },
                    theme: {
                        color: "#10b981"
                    }
                };

                window.openRideXCheckout(options);
            } catch (e) {
                console.error("Payment initiation error:", e);
                alert("Failed to connect to payment server.");
            }
        })();
    };
})();
