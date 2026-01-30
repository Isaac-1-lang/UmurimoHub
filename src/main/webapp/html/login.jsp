<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - UmurimoHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="form-container">
        <div style="text-align: center; margin-bottom: 2rem;">
            <img src="${pageContext.request.contextPath}/assets/logo.png" alt="UmurimoHub Logo" style="height: 80px; margin-bottom: 1rem;">
            <h1 style="color: #667eea;">UmurimoHub</h1>
            <h2>Login</h2>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/Login" method="POST">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <button type="submit" class="btn btn-primary" style="width: 100%;">Login</button>
        </form>

        <div style="text-align: center; margin-top: 1.5rem;">
            <p>New CEO? <a href="${pageContext.request.contextPath}/Register">Register here</a></p>
        </div>
    </div>
</body>
</html>
