let ws;
let currentChatUserId = null;
const currentUserId = document.getElementById('currentUserId').value;
const isSecure = window.location.protocol === 'https:';
const wsProtocol = isSecure ? 'wss://' : 'ws://';
const contextPath = document.getElementById('contextPath').value;
const wsUrl = wsProtocol + window.location.host + contextPath + "/ws/chat/" + currentUserId;

// Initialize WebSocket
function initWebSocket() {
    ws = new WebSocket(wsUrl);

    ws.onopen = function () {
        console.log("WebSocket connected.");
    };

    ws.onmessage = function (event) {
        const payload = JSON.parse(event.data);
        
        if (payload.type === 'history') {
            renderHistory(payload.data);
        } else if (payload.type === 'new_message') {
            const msg = payload.data;
            handleIncomingMessage(msg);
        }
    };

    ws.onclose = function () {
        console.log("WebSocket disconnected. Reconnecting...");
        showToast("Connection lost. Reconnecting...", "error");
        setTimeout(initWebSocket, 3000);
    };

    ws.onerror = function (error) {
        console.error("WebSocket error:", error);
    };
}

// Simple toast notification helper if not already defined globally
function showToast(message, type = "success") {
    const existing = document.getElementById('chat-toast');
    if (existing) existing.remove();
    
    const toast = document.createElement('div');
    toast.id = 'chat-toast';
    toast.style.position = 'fixed';
    toast.style.bottom = '20px';
    toast.style.right = '20px';
    toast.style.padding = '12px 24px';
    toast.style.background = type === 'error' ? 'rgba(239, 68, 68, 0.9)' : 'rgba(16, 185, 129, 0.9)';
    toast.style.color = 'white';
    toast.style.borderRadius = '8px';
    toast.style.zIndex = '1000';
    toast.style.boxShadow = '0 4px 12px rgba(0,0,0,0.15)';
    toast.style.backdropFilter = 'blur(10px)';
    toast.style.animation = 'fadeIn 0.3s ease-out';
    toast.innerText = message;
    
    document.body.appendChild(toast);
    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transition = 'opacity 0.3s ease';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// Select a contact to chat with
function selectContact(element) {
    // UI selection
    document.querySelectorAll('.contact-item').forEach(el => el.classList.remove('active'));
    element.classList.add('active');

    // Remove badge
    const badgeId = element.getAttribute('data-id');
    const badge = document.getElementById('badge-' + badgeId);
    if(badge) {
        badge.style.display = 'none';
        badge.innerText = '0';
    }

    // Set active user
    currentChatUserId = element.getAttribute('data-id');
    const contactName = element.getAttribute('data-name');

    // Update Chat UI
    document.getElementById('no-chat-selected').style.display = 'none';
    document.getElementById('chat-header').style.display = 'flex';
    document.getElementById('messages-container').style.display = 'flex';
    document.getElementById('chat-input-area').style.display = 'flex';
    
    document.getElementById('active-chat-name').innerText = contactName;
    document.getElementById('active-chat-avatar').innerText = contactName.charAt(0);
    
    // Enable input
    document.getElementById('message-input').disabled = false;
    document.getElementById('send-btn').disabled = false;
    document.getElementById('messages-container').innerHTML = ''; // Clear loading

    // Request History
    if (ws && ws.readyState === WebSocket.OPEN) {
        ws.send(JSON.stringify({
            type: 'history_request',
            receiverId: currentChatUserId
        }));
    }
    
    // Auto focus the input field
    setTimeout(() => {
        document.getElementById('message-input').focus();
    }, 100);
}

// Render conversation history
function renderHistory(messages) {
    const container = document.getElementById('messages-container');
    container.innerHTML = ''; // Clear

    messages.forEach(msg => {
        appendMessageUI(msg);
    });
    
    scrollChatToBottom();
}

// Handle real-time incoming message
function handleIncomingMessage(msg) {
    // If the message is from the person we are currently chatting with OR was sent by us
    if ((msg.senderId === currentChatUserId && msg.receiverId === currentUserId) || 
        (msg.senderId === currentUserId && msg.receiverId === currentChatUserId)) {
        
        appendMessageUI(msg);
        scrollChatToBottom();
        
        // If it's incoming and we are viewing it, mark as read
        if(msg.senderId === currentChatUserId && ws && ws.readyState === WebSocket.OPEN) {
            ws.send(JSON.stringify({
                type: 'mark_read',
                partnerId: currentChatUserId
            }));
        }
    } else {
        // Notification for a different chat
        if (msg.senderId !== currentUserId) {
            const badge = document.getElementById('badge-' + msg.senderId);
            if(badge) {
                let count = parseInt(badge.innerText) || 0;
                badge.innerText = count + 1;
                badge.style.display = 'block';
            }
            
            // Optional: Show browser notification
            if (Notification.permission === "granted") {
                new Notification("New message", {
                    body: msg.content
                });
            } else if (Notification.permission !== "denied") {
                Notification.requestPermission();
            }
        }
    }
}

function appendMessageUI(msg) {
    const container = document.getElementById('messages-container');
    const msgDiv = document.createElement('div');
    
    const isSent = msg.senderId === currentUserId;
    msgDiv.className = 'message ' + (isSent ? 'sent' : 'received');
    
    const time = new Date(msg.timestamp).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
    
    msgDiv.innerHTML = `
        <div class="message-content">${escapeHTML(msg.content)}</div>
        <span class="message-time">${time}</span>
    `;
    
    container.appendChild(msgDiv);
}

// Send message
function sendMessage() {
    const input = document.getElementById('message-input');
    const content = input.value.trim();
    
    if (content && currentChatUserId && ws && ws.readyState === WebSocket.OPEN) {
        ws.send(JSON.stringify({
            type: 'message',
            receiverId: currentChatUserId,
            content: content
        }));
        
        input.value = '';
        input.focus();
    }
}

function handleKeyPress(e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
}

function scrollChatToBottom() {
    const container = document.getElementById('messages-container');
    setTimeout(() => {
        container.scrollTo({
            top: container.scrollHeight,
            behavior: 'smooth'
        });
    }, 10);
}

function escapeHTML(str) {
    return str.replace(/[&<>'"]/g, 
        tag => ({
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            "'": '&#39;',
            '"': '&quot;'
        }[tag]));
}

// Request Notification Permission on load
document.addEventListener('DOMContentLoaded', () => {
    initWebSocket();
    if ("Notification" in window && Notification.permission !== "granted" && Notification.permission !== "denied") {
        Notification.requestPermission();
    }
});
