<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CEO Registration - UmurimoHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/logo.png">
</head>
<body>
    <div class="form-container">
        <div style="text-align: center; margin-bottom: 2rem;">
            <img src="${pageContext.request.contextPath}/assets/logo.png" alt="UmurimoHub Logo" style="height: 80px; margin-bottom: 1rem;">
            <h1 style="color: #667eea;">UmurimoHub</h1>
            <h2>CEO Registration</h2>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/Register" method="POST">
            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" required>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>

            <button type="submit" class="btn btn-primary" style="width: 100%;">Register</button>
        </form>

        <div style="text-align: center; margin-top: 1.5rem;">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/Login">Login here</a></p>
        </div>
    </div>
</body>
</html>
