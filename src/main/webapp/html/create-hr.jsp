<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Create HR Account" />
</jsp:include>

<div class="container">
    <jsp:include page="../includes/sidebar-ceo.jsp" />
    
    <div class="main-content">
        <h1 class="page-title">Create HR Account</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">Create New HR User</h2>
        <form action="${pageContext.request.contextPath}/CreateHR" method="POST" style="max-width: 600px; margin-bottom: 2rem;">
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
            <button type="submit" class="btn btn-primary">Create HR Account</button>
        </form>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">All HR Users</h2>
        <c:if test="${not empty hrUsers}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Status</th>
                        <th>Password Changed</th>
                        <th>Created At</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="hr" items="${hrUsers}">
                        <tr>
                            <td>${hr.firstName} ${hr.lastName}</td>
                            <td>${hr.email}</td>
                            <td>${hr.status}</td>
                            <td>${hr.passwordChanged ? 'Yes' : 'No'}</td>
                            <td><fmt:formatDate value="${hr.createdAt}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty hrUsers}">
            <p>No HR users found.</p>
        </c:if>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />
