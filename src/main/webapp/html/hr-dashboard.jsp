<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="HR Dashboard" />
</jsp:include>

<div class="container">
    <jsp:include page="../includes/sidebar-hr.jsp" />
    
    <div class="main-content">
        <h1 class="page-title">HR Dashboard</h1>
        <p>Welcome to your HR dashboard. Use the sidebar to navigate to different sections.</p>
        
        <div class="stats-grid" style="margin-top: 2rem;">
            <div class="stat-card">
                <h3>Quick Actions</h3>
                <p style="margin-top: 1rem;">
                    <a href="${pageContext.request.contextPath}/HRWorker" class="btn btn-primary" style="display: block; margin-bottom: 0.5rem;">Manage Workers</a>
                    <a href="${pageContext.request.contextPath}/Attendance" class="btn btn-primary" style="display: block; margin-bottom: 0.5rem;">Record Attendance</a>
                    <a href="${pageContext.request.contextPath}/Deduction" class="btn btn-primary" style="display: block; margin-bottom: 0.5rem;">Create Deduction</a>
                    <a href="${pageContext.request.contextPath}/Punishment" class="btn btn-primary" style="display: block;">Record Punishment</a>
                </p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />
