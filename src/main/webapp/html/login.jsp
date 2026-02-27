<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login  UmurimoHub</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <link rel="icon" href="<c:url value='/assets/logo.png'/>">
</head>
<body class="auth-body">
<div class="form-container">
    <div class="auth-header">
        <img src="<c:url value='/assets/logo.png'/>" alt="UmurimoHub Logo" class="auth-logo">
        <h1 class="auth-title">UmurimoHub</h1>
        <p class="auth-subtitle">Work is key to success and richness</p>
        <h2 class="auth-heading">Login</h2>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}" /></div>
    </c:if>

    <form action="<c:url value='/Login'/>" method="POST">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="captcha">CAPTCHA</label>
            <div style="display:flex; gap: 1rem; align-items:center;">
                <img src="<c:url value='/captcha'/>" alt="CAPTCHA" style="border:1px solid #ddd; border-radius:6px; height:38px;">
                <input type="text" id="captcha" name="captcha" placeholder="Enter the text" required style="flex:1;">
            </div>
            <small style="display:block; margin-top:0.5rem;">
                If the image is hard to read, refresh the page to get a new one.
            </small>
        </div>

        <button type="submit" class="btn btn-primary" style="width: 100%;">Login</button>
    </form>

    <div style="text-align: center; margin-top: 1.5rem;">
        <p>New CEO? <a href="<c:url value='/Register'/>">Register here</a></p>
    </div>
</div>
</body>
</html>