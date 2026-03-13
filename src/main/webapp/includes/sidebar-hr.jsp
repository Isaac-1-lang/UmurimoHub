<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<aside class="sidebar">
    <nav class="sidebar-nav">
        <ul>
            <li><a href="<c:url value='/html/hr-dashboard.jsp'/>" class="nav-link">Dashboard</a></li>
            <li><a href="<c:url value='/HRWorker'/>" class="nav-link">Manage Workers</a></li>
            <li><a href="<c:url value='/Attendance'/>" class="nav-link">Attendance</a></li>
            <li><a href="<c:url value='/Deduction'/>" class="nav-link">Deductions</a></li>
            <li><a href="<c:url value='/Punishment'/>" class="nav-link">Punishments</a></li>
            <li><a href="<c:url value='/Chat'/>" class="nav-link">Chat</a></li>
        </ul>
    </nav>
</aside>
