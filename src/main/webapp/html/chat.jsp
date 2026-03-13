<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/Login");
        return;
    }
    String currentUserId = (String) request.getAttribute("currentUserId");
%>
<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Messages" />
</jsp:include>

<style>
/* Light Theme Chat Overrides to match reference exactly */
.chat-main-wrapper {
    padding: 0 !important;
    overflow: hidden !important;
    background: #ffffff !important;
    border: 1px solid #e2e8f0 !important;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1) !important;
}

.chat-wrapper {
    display: flex;
    height: 100%;
    width: 100%;
    min-height: calc(100vh - 120px);
    background: #ffffff;
    font-family: 'Inter', sans-serif;
}

.contacts-sidebar {
    width: 320px;
    border-right: 1px solid #f1f5f9;
    display: flex;
    flex-direction: column;
    background: #f8fafc; /* Very light grey for sidebar */
}

.contacts-header {
    padding: 20px;
    border-bottom: 1px solid #f1f5f9;
}

/* Rounded search input like reference */
.search-container {
    position: relative;
    width: 100%;
}

.search-container i {
    position: absolute;
    left: 14px;
    top: 50%;
    transform: translateY(-50%);
    color: #94a3b8;
    font-size: 0.9rem;
}

.search-input {
    width: 100%;
    padding: 10px 15px 10px 38px;
    border-radius: 20px; /* Pill shape */
    border: 1px solid #e2e8f0;
    background: #ffffff;
    color: #1e293b;
    font-size: 0.9rem;
    outline: none;
    transition: all 0.2s;
}

.search-input:focus {
    border-color: #6366f1;
    box-shadow: 0 0 0 2px rgba(99, 102, 241, 0.2);
}

.search-input::placeholder {
    color: #94a3b8;
}

.contact-list {
    flex: 1;
    overflow-y: auto;
    padding: 10px 0;
}

/* Custom scrollbar */
.contact-list::-webkit-scrollbar, .messages-container::-webkit-scrollbar {
    width: 6px;
}
.contact-list::-webkit-scrollbar-track, .messages-container::-webkit-scrollbar-track {
    background: transparent;
}
.contact-list::-webkit-scrollbar-thumb, .messages-container::-webkit-scrollbar-thumb {
    background: #cbd5e1;
    border-radius: 3px;
}

.contact-item {
    padding: 12px 20px;
    cursor: pointer;
    transition: background 0.2s;
    display: flex;
    align-items: center;
    gap: 15px;
    margin: 2px 10px;
    border-radius: 8px;
}

.contact-item:hover {
    background: #f1f5f9;
}

.contact-item.active {
    background: #eef2ff;
}

.contact-avatar {
    width: 42px;
    height: 42px;
    border-radius: 50%;
    background: #eef2ff; /* Light blue */
    color: #4f46e5; /* Intersecting blue */
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 600;
    font-size: 1.1rem;
    flex-shrink: 0;
    position: relative;
}

.status-dot {
    position: absolute;
    bottom: 0;
    right: 0;
    width: 12px;
    height: 12px;
    border-radius: 50%;
    background: #94a3b8; /* Offline grey by default */
    border: 2px solid #ffffff;
}
.status-dot.online {
    background: #10b981; /* Online green */
}

.contact-info {
    flex: 1;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.contact-name {
    font-weight: 600;
    color: #1e293b; /* Dark text for light theme */
    font-size: 0.95rem;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-bottom: 2px;
}

.contact-role {
    font-size: 0.8rem;
    color: #64748b;
    display: flex;
    align-items: center;
    gap: 5px;
}

.unread-badge {
    background: #6366f1;
    color: white;
    padding: 2px 8px;
    border-radius: 12px;
    font-size: 0.75rem;
    font-weight: 600;
    display: none;
    box-shadow: 0 2px 4px rgba(99, 102, 241, 0.3);
}

.chat-area {
    flex: 1;
    display: flex;
    flex-direction: column;
    background: #ffffff;
    position: relative;
}

.no-chat-selected {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    color: #64748b;
}

.empty-state-icon {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    background: #eef2ff;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 20px;
    color: #6366f1;
    font-size: 2.5rem;
}

.no-chat-selected h2 {
    color: #0f172a;
    font-size: 1.5rem;
    font-weight: 600;
    margin-bottom: 10px;
}

.no-chat-selected p {
    color: #64748b;
    font-size: 0.95rem;
}

.chat-header {
    padding: 15px 25px;
    background: #ffffff;
    border-bottom: 1px solid #f1f5f9;
    display: flex;
    align-items: center;
    gap: 15px;
    height: 75px;
    box-shadow: 0 1px 3px rgba(0,0,0,0.02);
}

.chat-header h3 {
    font-size: 1.1rem;
    color: #1e293b;
    margin: 0;
    font-weight: 600;
}

.messages-container {
    flex: 1;
    padding: 20px 25px;
    overflow-y: auto;
    display: flex;
    flex-direction: column;
    gap: 15px;
    display: none;
    background: #ffffff;
}

.message {
    max-width: 70%;
    padding: 12px 18px;
    border-radius: 16px;
    position: relative;
    animation: fadeIn 0.3s ease;
    box-shadow: 0 1px 2px rgba(0,0,0,0.05);
}

@keyframes fadeIn {
    from { opacity: 0; transform: translateY(10px); }
    to { opacity: 1; transform: translateY(0); }
}

.message.sent {
    align-self: flex-end;
    background: #6366f1;
    color: white;
    border-bottom-right-radius: 4px;
}

.message.received {
    align-self: flex-start;
    background: #f1f5f9;
    color: #1e293b;
    border-bottom-left-radius: 4px;
}

.message-content {
    font-size: 0.95rem;
    line-height: 1.5;
    word-wrap: break-word;
}

.message-time {
    font-size: 0.7rem;
    opacity: 0.7;
    margin-top: 5px;
    display: block;
    text-align: right;
}

.chat-input-area {
    padding: 15px 25px;
    background: #ffffff;
    border-top: 1px solid #f1f5f9;
    display: none;
    gap: 15px;
    align-items: center;
}

.chat-input {
    flex: 1;
    padding: 12px 20px;
    background: #f8fafc;
    border: 1px solid #e2e8f0;
    border-radius: 24px;
    outline: none;
    font-size: 0.95rem;
    color: #1e293b;
    transition: all 0.2s;
}

.chat-input:focus {
    border-color: #6366f1;
    background: #ffffff;
    box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
}

.chat-input::placeholder {
    color: #94a3b8;
}

.send-btn {
    background: #6366f1;
    color: white;
    border: none;
    width: 44px;
    height: 44px;
    border-radius: 50%;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.2s;
    font-size: 1.1rem;
    flex-shrink: 0;
}

.send-btn:hover {
    transform: scale(1.05);
    background: #4f46e5;
}

.send-btn:active {
    transform: scale(0.95);
}

.send-btn:disabled {
    background: #cbd5e1;
    cursor: not-allowed;
    transform: none;
}
</style>

<div class="container">
    <c:choose>
        <c:when test="${sessionScope.role == 'CEO'}">
            <jsp:include page="/includes/sidebar-ceo.jsp" />
        </c:when>
        <c:when test="${sessionScope.role == 'HR'}">
            <jsp:include page="/includes/sidebar-hr.jsp" />
        </c:when>
        <c:otherwise>
            <jsp:include page="/includes/sidebar-worker.jsp" />
        </c:otherwise>
    </c:choose>

    <div class="main-content chat-main-wrapper">
        <div class="chat-wrapper">
            <!-- Sidebar: Contacts -->
            <div class="contacts-sidebar">
                <div class="contacts-header">
                    <div class="search-container">
                        <i class="fas fa-search"></i>
                        <input type="text" class="search-input" placeholder="Search people...">
                    </div>
                </div>
                <div class="contact-list">
                    <c:forEach var="contact" items="${contacts}">
                        <!-- Flex layout contact item matching reference exactly -->
                        <div class="contact-item" data-id="${contact.id}" data-name="${contact.name}" onclick="selectContact(this)">
                            <div class="contact-avatar">
                                ${contact.name.substring(0, 1)}
                                <div class="status-dot"></div>
                            </div>
                            <div class="contact-info">
                                <div class="contact-name">${contact.name}</div>
                                <div class="contact-role">
                                    <i class="far fa-circle" style="font-size: 0.6rem;"></i> Offline
                                </div>
                            </div>
                            <div class="unread-badge" id="badge-${contact.id}">0</div>
                        </div>
                    </c:forEach>
                    <c:if test="${empty contacts}">
                        <div style="padding: 20px; color: #94a3b8; text-align: center;">No contacts available.</div>
                    </c:if>
                </div>
            </div>

            <!-- Chat Area -->
            <div class="chat-area">
                <div class="no-chat-selected" id="no-chat-selected">
                    <div class="empty-state-icon">
                        <i class="far fa-comment-dots"></i>
                    </div>
                    <h2>Start a conversation</h2>
                    <p>Select a chat or search for someone to begin messaging.</p>
                </div>

                <div class="chat-header" id="chat-header" style="display: none;">
                    <div class="contact-avatar" id="active-chat-avatar" style="width: 36px; height: 36px; font-size: 0.9rem;"></div>
                    <div style="flex: 1;">
                        <h3 id="active-chat-name"></h3>
                        <span style="font-size: 0.8rem; color: #10b981;" id="active-chat-status">Online</span>
                    </div>
                </div>

                <div class="messages-container" id="messages-container">
                    <!-- Messages render here -->
                </div>

                <div class="chat-input-area" id="chat-input-area">
                    <input type="text" id="message-input" class="chat-input" placeholder="Type your message..." onkeypress="handleKeyPress(event)" autocomplete="off">
                    <button class="send-btn" id="send-btn" onclick="sendMessage()" disabled title="Send">
                        <i class="fas fa-paper-plane"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Hidden inputs to store current user info for JS -->
<input type="hidden" id="currentUserId" value="${currentUserId}">
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

<script src="${pageContext.request.contextPath}/js/chat.js"></script>

<jsp:include page="/includes/footer.jsp" />
