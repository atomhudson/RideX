/**
 * RideX In-App Chat Drawer
 * ========================
 * Works for both Driver (opens chat with a passenger) and
 * Passenger (opens chat with the driver).
 *
 * Usage (from any template):
 *   openChat(rideId, otherUserId, otherName, otherAvatar)
 */

(function () {

    /* ── Inject chat drawer HTML into DOM once ── */
    function injectDrawer() {
        if (document.getElementById('rxChatDrawer')) return;

        const html = `
        <!-- RideX Chat Drawer -->
        <div id="rxChatDrawer" style="
            position:fixed; bottom:0; right:0; width:360px; max-width:100vw;
            height:520px; max-height:90vh;
            background:var(--rx-bg-secondary);
            border:1px solid var(--rx-border);
            border-radius:16px 16px 0 0;
            box-shadow:0 -8px 40px rgba(0,0,0,0.35);
            display:flex; flex-direction:column;
            z-index:9999; transform:translateY(100%);
            transition:transform 0.3s cubic-bezier(.4,0,.2,1);
        ">
            <!-- Header -->
            <div id="rxChatHeader" style="
                display:flex; align-items:center; gap:12px;
                padding:14px 16px;
                border-bottom:1px solid var(--rx-border);
                background:var(--rx-bg-tertiary);
                border-radius:16px 16px 0 0;
                flex-shrink:0;
            ">
                <img id="rxChatAvatar" src="" alt=""
                     style="width:36px;height:36px;border-radius:50%;object-fit:cover;flex-shrink:0;" />
                <div style="flex:1;min-width:0;">
                    <div id="rxChatName" style="font-size:0.9rem;font-weight:700;color:var(--rx-text-primary);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"></div>
                    <div style="font-size:0.72rem;color:var(--rx-text-muted);">In-Ride Chat · Secure & Private</div>
                </div>
                <button onclick="closeChat()" style="
                    background:rgba(239,68,68,0.1);border:1px solid rgba(239,68,68,0.25);
                    color:#ef4444;border-radius:8px;padding:5px 10px;cursor:pointer;font-size:0.8rem;
                ">✕</button>
            </div>

            <!-- Quick replies -->
            <div style="display:flex;gap:6px;padding:10px 12px;flex-wrap:wrap;border-bottom:1px solid var(--rx-border);flex-shrink:0;">
                <button class="rx-quick-reply" onclick="sendQuickReply('I am at the pickup point 📍')">📍 At pickup</button>
                <button class="rx-quick-reply" onclick="sendQuickReply('Running 5 mins late ⏳')">⏳ Running late</button>
                <button class="rx-quick-reply" onclick="sendQuickReply('On my way! 🚗')">🚗 On my way</button>
            </div>

            <!-- Messages area -->
            <div id="rxChatMessages" style="
                flex:1; overflow-y:auto; padding:14px 12px;
                display:flex; flex-direction:column; gap:10px;
                scrollbar-width:thin;
            ">
                <div id="rxChatLoading" style="text-align:center;color:var(--rx-text-muted);font-size:0.82rem;padding:20px 0;">
                    <i class="fa-solid fa-circle-notch fa-spin"></i> Loading messages…
                </div>
            </div>

            <!-- Input row -->
            <div style="
                display:flex; gap:8px; padding:12px;
                border-top:1px solid var(--rx-border);
                background:var(--rx-bg-tertiary);
                flex-shrink:0;
            ">
                <input id="rxChatInput" type="text"
                       placeholder="Type a message…"
                       maxlength="500"
                       style="
                           flex:1; background:var(--rx-bg-primary); border:1px solid var(--rx-border);
                           border-radius:10px; padding:9px 14px; font-size:0.875rem;
                           color:var(--rx-text-primary); outline:none;
                       "
                       onkeydown="if(event.key==='Enter' && !event.shiftKey){event.preventDefault();sendChatMessage();}"
                />
                <button onclick="sendChatMessage()" style="
                    background:var(--rx-accent); color:#fff; border:none;
                    border-radius:10px; padding:9px 16px; cursor:pointer;
                    font-size:0.875rem; font-weight:600; transition:opacity 0.2s;
                " onmouseover="this.style.opacity='.85'" onmouseout="this.style.opacity='1'">
                    <i class="fa-solid fa-paper-plane"></i>
                </button>
            </div>
        </div>`;

        document.body.insertAdjacentHTML('beforeend', html);

        /* Quick-reply button style */
        const style = document.createElement('style');
        style.textContent = `
            .rx-quick-reply {
                font-size: 0.72rem; padding: 5px 10px; border-radius: 20px;
                background: rgba(99,102,241,0.08); color: var(--rx-accent);
                border: 1px solid rgba(99,102,241,0.25); cursor: pointer;
                transition: background 0.2s; white-space: nowrap;
            }
            .rx-quick-reply:hover { background: rgba(99,102,241,0.18); }
            .rx-bubble-wrap { display:flex; align-items:flex-end; gap:8px; }
            .rx-bubble-wrap.mine { flex-direction: row-reverse; }
            .rx-bubble-avatar { width:26px;height:26px;border-radius:50%;object-fit:cover;flex-shrink:0; }
            .rx-bubble {
                max-width: 68%; padding: 9px 13px; border-radius: 14px;
                font-size: 0.85rem; line-height: 1.45; word-break: break-word;
            }
            .rx-bubble.theirs {
                background: var(--rx-bg-tertiary);
                border: 1px solid var(--rx-border);
                color: var(--rx-text-primary);
                border-bottom-left-radius: 4px;
            }
            .rx-bubble.mine {
                background: var(--rx-accent);
                color: #fff;
                border-bottom-right-radius: 4px;
            }
            .rx-bubble-time { font-size:0.65rem; color:var(--rx-text-muted); text-align:right; margin-top:3px; }
        `;
        document.head.appendChild(style);
    }

    /* ── State ── */
    let _rideId = null, _otherId = null, _myId = null, _pollTimer = null;

    /* ── Public API ── */
    window.openChat = function (rideId, otherId, otherName, otherAvatar, myUserId) {
        injectDrawer();
        _rideId = rideId;
        _otherId = otherId;
        _myId = myUserId || (document.body ? document.body.getAttribute('data-user-id') : null);

        document.getElementById('rxChatName').textContent   = otherName   || 'Chat';
        document.getElementById('rxChatAvatar').src          = otherAvatar || '/images/default-avatar.png';
        document.getElementById('rxChatDrawer').style.transform = 'translateY(0)';

        loadMessages();

        // Poll for new messages every 5 seconds
        clearInterval(_pollTimer);
        _pollTimer = setInterval(loadMessages, 5000);
    };

    window.closeChat = function () {
        const drawer = document.getElementById('rxChatDrawer');
        if (drawer) drawer.style.transform = 'translateY(100%)';
        clearInterval(_pollTimer);
    };

    /* ── Load messages from server ── */
    function loadMessages() {
        if (!_rideId || !_otherId) return;
        fetch(`/api/chat/messages/${_rideId}/${_otherId}`)
            .then(r => r.json())
            .then(messages => renderMessages(messages))
            .catch(() => {});
    }

    /* ── Render messages ── */
    function renderMessages(messages) {
        const box = document.getElementById('rxChatMessages');
        if (!box) return;

        if (messages.length === 0) {
            box.innerHTML = `<div style="text-align:center;padding:30px 0;color:var(--rx-text-muted);font-size:0.85rem;">
                <i class="fa-solid fa-comments" style="font-size:1.8rem;display:block;margin-bottom:8px;opacity:0.4;"></i>
                No messages yet. Say hi! 👋</div>`;
            return;
        }

        const wasAtBottom = box.scrollHeight - box.clientHeight <= box.scrollTop + 40;
        const myId = _myId || (document.body ? document.body.getAttribute('data-user-id') : null);

        box.innerHTML = messages.map(msg => {
            const isMine = myId ? (msg.senderId === myId) : (msg.senderId !== _otherId);
            const time   = new Date(msg.sentAt).toLocaleTimeString([], {hour:'2-digit', minute:'2-digit'});
            return `
            <div class="rx-bubble-wrap ${isMine ? 'mine' : ''}">
                <img class="rx-bubble-avatar" src="${msg.senderAvatar || '/images/default-avatar.png'}" alt="" />
                <div>
                    <div class="rx-bubble ${isMine ? 'mine' : 'theirs'}">${escapeHtml(msg.messageText)}</div>
                    <div class="rx-bubble-time">${time}</div>
                </div>
            </div>`;
        }).join('');

        if (wasAtBottom) box.scrollTop = box.scrollHeight;
    }

    /* ── Send a message ── */
    window.sendChatMessage = function () {
        const input = document.getElementById('rxChatInput');
        const text  = input ? input.value.trim() : '';
        if (!text || !_rideId || !_otherId) return;

        input.value = '';

        fetch('/api/chat/send', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': getCsrf() },
            body: JSON.stringify({ rideId: _rideId, recipientId: _otherId, messageText: text })
        })
        .then(r => r.json())
        .then(msg => {
            // Append sent bubble immediately without waiting for poll
            const box = document.getElementById('rxChatMessages');
            if (!box) return;
            const time = new Date(msg.sentAt).toLocaleTimeString([], {hour:'2-digit', minute:'2-digit'});
            const div  = document.createElement('div');
            div.className = 'rx-bubble-wrap mine';
            div.innerHTML = `
                <img class="rx-bubble-avatar" src="${msg.senderAvatar || '/images/default-avatar.png'}" alt="" />
                <div>
                    <div class="rx-bubble mine">${escapeHtml(msg.messageText)}</div>
                    <div class="rx-bubble-time">${time}</div>
                </div>`;
            // Remove empty state if present
            const empty = box.querySelector('div[style*="No messages"]');
            if (empty) empty.remove();
            box.appendChild(div);
            box.scrollTop = box.scrollHeight;
        })
        .catch(() => alert('Failed to send message. Please try again.'));
    };

    window.sendQuickReply = function (text) {
        const input = document.getElementById('rxChatInput');
        if (input) { input.value = text; sendChatMessage(); }
    };

    /* ── Helpers ── */
    function escapeHtml(str) {
        return str.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;')
                  .replace(/"/g,'&quot;').replace(/'/g,'&#39;');
    }

    function getCsrf() {
        const meta = document.querySelector('meta[name="_csrf"]');
        return meta ? meta.getAttribute('content') : '';
    }

})();
