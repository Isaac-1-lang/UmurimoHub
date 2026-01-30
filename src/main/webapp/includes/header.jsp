<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>UmurimoHub - ${param.title != null ? param.title : 'Dashboard'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header class="main-header">
        <div class="header-content">
            <div class="logo-section">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="UmurimoHub Logo" class="logo">
                <h1>UmurimoHub</h1>
            </div>
            <div class="user-section">
                <c:if test="${sessionScope.user != null}">
                    <span class="user-name">Welcome, ${sessionScope.firstName} ${sessionScope.lastName}</span>
                    <span class="user-role">(${sessionScope.role})</span>
                    <a href="${pageContext.request.contextPath}/Logout" class="btn-logout">Logout</a>
                </c:if>
            </div>
        </div>
    </header>
