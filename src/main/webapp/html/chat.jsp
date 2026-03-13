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
/* Replace your current chat <style> block with this */
.chat-main-wrapper { padding: 0 !important; overflow: hidden; }
.chat-wrapper { display: flex; height: calc(100vh - 120px); min-height: 500px; }

.contacts-sidebar {
    width: 300px;
    background: rgba(17, 24, 39, 0.7);
    border-right: 1px solid rgba(255,255,255,0.06);
    display: flex; flex-direction: column;
}
.contacts-header {
    padding: 18px 16px 12px;
    border-bottom: 1px solid rgba(255,255,255,0.06);
    display: flex; flex-direction: column; gap: 12px;
}
.contact-item {
    display: flex; align-items: center; gap: 12px;
    padding: 10px 16px; cursor: pointer;
    transition: background 0.15s;
}
.contact-item:hover { background: rgba(255,255,255,0.04); }
.contact-item.active { background: rgba(99,102,241,0.12); border-left: 3px solid #6366f1; }

.contact-avatar {
    width: 38px; height: 38px; border-radius: 50%;
    background: rgba(99,102,241,0.2); color: #a5b4fc;
    display: flex; align-items: center; justify-content: center;
    font-weight: 600; font-size: 14px; flex-shrink: 0; position: relative;
}

/* Message bubbles */
.message.sent {
    align-self: flex-end;
    background: linear-gradient(135deg, #4f46e5, #6366f1);
    border-radius: 18px 18px 4px 18px;
    box-shadow: 0 2px 12px rgba(99,102,241,0.25);
}
.message.received {
    align-self: flex-start;
    background: rgba(30, 37, 53, 0.9);
    border: 1px solid rgba(255,255,255,0.07);
    border-radius: 18px 18px 18px 4px;
}

/* Input */
.chat-input { border-radius: 12px; }
.send-btn {
    background: #6366f1; width: 38px; height: 38px;
    border-radius: 10px; box-shadow: 0 2px 8px rgba(99,102,241,0.35);
}
.send-btn:hover { background: #4f46e5; transform: scale(1.05); box-shadow: 0 4px 14px rgba(99,102,241,0.5); }
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
                <div class="contacts-header" style="flex-direction: column; align-items: stretch; gap: 15px;">
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <span>Messages</span>
                        <i class="fas fa-edit" style="color: var(--primary); cursor: pointer;" title="New Message"></i>
                    </div>
                    <!-- Added search input to match reference -->
                    <div style="position: relative;">
                        <i class="fas fa-search" style="position: absolute; left: 12px; top: 50%; transform: translateY(-50%); color: var(--text-muted); font-size: 0.9rem;"></i>
                        <input type="text" placeholder="Search people..." style="width: 100%; padding: 10px 10px 10px 35px; border-radius: 8px; border: 1px solid var(--glass-border); background: var(--input-bg); color: var(--text-color); font-size: 0.9rem; outline: none;">
                    </div>
                </div>
                <div class="contact-list">
                    <c:forEach var="contact" items="${contacts}">
                        <!-- Ensure flex layout prevents wrapping -->
                        <div class="contact-item" data-id="${contact.id}" data-name="${contact.name}" onclick="selectContact(this)" style="display: flex; align-items: center; gap: 12px; padding: 12px 20px;">
                            <div class="contact-avatar" style="width: 40px; height: 40px; border-radius: 50%; background: rgba(79, 70, 229, 0.2); color: var(--primary); display: flex; align-items: center; justify-content: center; font-weight: 600; font-size: 1rem; flex-shrink: 0; box-shadow: none; position: relative;">
                                ${contact.name.substring(0, 1)}
                                <!-- Offline status dot to match reference -->
                                <div style="position: absolute; bottom: 0; right: 0; width: 10px; height: 10px; border-radius: 50%; background: #94a3b8; border: 2px solid var(--card-bg);"></div>
                            </div>
                            <div class="contact-info" style="flex: 1; overflow: hidden; display: flex; flex-direction: column; justify-content: center;">
                                <div class="contact-name" style="font-weight: 600; color: var(--text-color); font-size: 0.95rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; margin-bottom: 2px;">${contact.name}</div>
                                <div class="contact-role" style="font-size: 0.75rem; color: var(--text-muted); display: flex; align-items: center; gap: 5px;">
                                    <i class="far fa-circle" style="font-size: 0.6rem;"></i> Offline
                                </div>
                            </div>
                            <div class="unread-badge" id="badge-${contact.id}">0</div>
                        </div>
                    </c:forEach>
                    <c:if test="${empty contacts}">
                        <div style="padding: 20px; color: var(--text-muted); text-align: center;">No contacts available.</div>
                    </c:if>
                </div>
            </div>

            <!-- Chat Area -->
            <div class="chat-area">
                <div class="no-chat-selected" id="no-chat-selected">
                    <i class="far fa-comments"></i>
                    <h2>Select a conversation</h2>
                    <p>Choose a contact from the sidebar to start chatting</p>
                </div>

                <div class="chat-header" id="chat-header" style="display: none;">
                    <div class="contact-avatar" id="active-chat-avatar"></div>
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
