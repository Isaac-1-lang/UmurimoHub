<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verify CEO OTP - UmurimoHub</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <link rel="icon" href="<c:url value='/assets/logo.png'/>">
</head>
<body class="auth-body">
<div class="form-container">
    <div style="text-align: center; margin-bottom: 1.5rem;">
        <h1 class="auth-title">UmurimoHub</h1>
        <h2 class="auth-heading">Verify Email (CEO)</h2>
        <p class="auth-subtitle">
            We've sent a one-time code (OTP) to your email. Enter it below to complete registration.
        </p>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}" /></div>
    </c:if>

    <form action="<c:url value='/VerifyCEOOTP'/>" method="POST">
        <div class="form-group">
            <label for="otp">One-Time Code</label>
            <input type="text" id="otp" name="otp" maxlength="6" autocomplete="one-time-code" required>
        </div>

        <button type="submit" class="btn btn-primary" style="width: 100%;">Verify &amp; Continue</button>
    </form>

    <div style="text-align: center; margin-top: 1.5rem;">
        <p style="font-size: 0.9rem; color: #777;">
            Didn't receive the code? Check your spam folder or try registering again.
        </p>
    </div>
</div>
</body>
</html>

