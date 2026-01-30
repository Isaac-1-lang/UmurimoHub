<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Manage Workers" />
</jsp:include>

<div class="container">
    <jsp:include page="../includes/sidebar-hr.jsp" />
    
    <div class="main-content">
        <h1 class="page-title">Manage Workers</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">Create New Worker</h2>
        <form action="${pageContext.request.contextPath}/HRWorker" method="POST" style="max-width: 600px; margin-bottom: 2rem;">
            <input type="hidden" name="action" value="create">
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
                <label for="phoneNumber">Phone Number</label>
                <input type="text" id="phoneNumber" name="phoneNumber">
            </div>
            <div class="form-group">
                <label for="baseSalary">Base Salary</label>
                <input type="number" id="baseSalary" name="baseSalary" min="0">
            </div>
            <button type="submit" class="btn btn-primary">Create Worker</button>
        </form>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">All Workers</h2>
        <c:if test="${not empty workers}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Base Salary</th>
                        <th>Hire Date</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="worker" items="${workers}">
                        <tr>
                            <td>${worker.firstName} ${worker.lastName}</td>
                            <td>${worker.email}</td>
                            <td>${worker.phoneNumber}</td>
                            <td>${worker.baseSalary}</td>
                            <td><fmt:formatDate value="${worker.hireDate}" pattern="yyyy-MM-dd" /></td>
                            <td>${worker.status}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty workers}">
            <p>No workers found.</p>
        </c:if>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />
