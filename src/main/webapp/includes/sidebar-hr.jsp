<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<aside class="sidebar">
    <nav class="sidebar-nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/html/hr-dashboard.jsp" class="nav-link">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/HRWorker" class="nav-link">Manage Workers</a></li>
            <li><a href="${pageContext.request.contextPath}/Attendance" class="nav-link">Attendance</a></li>
            <li><a href="${pageContext.request.contextPath}/Deduction" class="nav-link">Deductions</a></li>
            <li><a href="${pageContext.request.contextPath}/Punishment" class="nav-link">Punishments</a></li>
        </ul>
    </nav>
</aside>
