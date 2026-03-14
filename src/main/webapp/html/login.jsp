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
    <!-- Google Identity Services for "Continue with Google" -->
    <script src="https://accounts.google.com/gsi/client" async defer></script>
</head>
<body class="auth-body">
<div class="form-container">
    <div class="auth-header">
        <img src="<c:url value= '/assets/logo.png'/>" alt="UmurimoHub Logo" class="auth-logo">
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
            <label for="captcha">Security Check</label>
            <div class="captcha-container">
                <img src="<c:url value='/captcha'/>" alt="CAPTCHA">
                <input type="text" id="captcha" name="captcha" placeholder="Enter characters" required>
            </div>
        </div>

        <button type="submit" class="btn btn-primary" style="width: 100%;">Login</button>
    </form>

    <!-- Divider -->
    <div style="margin: 1.5rem 0; display: flex; align-items: center; gap: 0.75rem;">
        <hr style="flex: 1; border: none; border-top: 1px solid rgba(148, 163, 184, 0.4);" />
        <span style="font-size: 0.8rem; text-transform: uppercase; letter-spacing: 0.08em; color: #94a3b8;">or</span>
        <hr style="flex: 1; border: none; border-top: 1px solid rgba(148, 163, 184, 0.4);" />
    </div>

    <!-- Google Sign-In button -->
    <div id="g_id_onload"
         data-client_id="189641498269-hsnqe1t654t1266daflqq25rhqdbgbe7.apps.googleusercontent.com"
         data-context="signin"
         data-ux_mode="popup"
         data-callback="handleGoogleCredentialResponse"
         data-auto_prompt="false">
    </div>
    <div class="g_id_signin"
         data-type="standard"
         data-shape="pill"
         data-theme="outline"
         data-text="continue_with"
         data-size="large"
         data-logo_alignment="left"
         style="width: 100%; display: flex; justify-content: center;">
    </div>

    <script>
        // Called by Google Identity Services when the user picks a Google account
        function handleGoogleCredentialResponse(response) {
            if (!response || !response.credential) {
                return;
            }
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '<c:url value="/GoogleLogin"/>';

            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'credential';
            input.value = response.credential;
            form.appendChild(input);

            document.body.appendChild(form);
            form.submit();
        }
    </script>

    <div style="text-align: center; margin-top: 1.5rem;">
        <p>New CEO? <a href="<c:url value='/Register'/>">Register here</a></p>
    </div>
</div>
</body>
</html>