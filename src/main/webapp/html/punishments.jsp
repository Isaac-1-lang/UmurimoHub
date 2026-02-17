<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Disciplinary Actions" />
</jsp:include>
<%--We have implicit objects and explicit objects where implicit are built in applicationScope, pageContext,request response and others while explicit are the ones that u make manually!!!--%>

<div class="container">
    <jsp:include page="../includes/sidebar-hr.jsp" />
    
    <div class="main-content">
        <h1 class="page-title">Disciplinary Actions</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">Record Disciplinary Action</h2>
        <form action="${pageContext.request.contextPath}/Punishment" method="POST" style="max-width: 600px; margin-bottom: 2rem;">
            <input type="hidden" name="action" value="create">
            <div class="form-group">
                <label for="workerId">Worker</label>
                <select id="workerId" name="workerId" required>
                    <option value="">Select Worker</option>
                    <c:forEach var="worker" items="${workers}">
                        <option value="${worker.workerId}">${worker.firstName} ${worker.lastName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" id="title" name="title" required>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" rows="4" required></textarea>
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <input type="date" id="date" name="date">
            </div>
            <button type="submit" class="btn btn-primary">Record Action</button>
        </form>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">All Disciplinary Actions</h2>
        <c:if test="${not empty punishments}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Worker</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="punishment" items="${punishments}">
                        <tr>
                            <td>${punishment.workerName}</td>
                            <td>${punishment.title}</td>
                            <td>${punishment.description}</td>
                            <td><fmt:formatDate value="${punishment.date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty punishments}">
            <p>No disciplinary actions found.</p>
        </c:if>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />
