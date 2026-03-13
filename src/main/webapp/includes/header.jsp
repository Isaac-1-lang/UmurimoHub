<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:choose>
        <c:when test="${not empty param.title}">
            <c:set var="pageTitle" value="${param.title}" />
        </c:when>
        <c:otherwise>
            <c:set var="pageTitle" value="Dashboard" />
        </c:otherwise>
    </c:choose>
    <title>UmurimoHub - <c:out value="${pageTitle}" /></title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
    <header class="main-header">
        <div class="header-content">
            <div class="logo-section">
                <img src="<c:url value='/assets/logo.png'/>" alt="UmurimoHub Logo" class="logo">
            </div>
            <div class="user-section">
                <c:if test="${sessionScope.user != null}">
                    <span class="user-name">Welcome, <c:out value="${sessionScope.firstName}" /> <c:out value="${sessionScope.lastName}" /></span>
                    <span class="user-role">(<c:out value="${sessionScope.role}" />)</span>
                    <a href="<c:url value='/Logout'/>" class="btn-logout">Logout</a>
                </c:if>
            </div>
        </div>
    </header>
