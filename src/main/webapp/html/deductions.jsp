<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Deductions Management" />
</jsp:include>

<div class="container">
    <jsp:include page="../includes/sidebar-hr.jsp" />
    
    <div class="main-content">
        <h1 class="page-title">Deductions Management</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">Create Deduction</h2>
        <form action="${pageContext.request.contextPath}/Deduction" method="POST" style="max-width: 600px; margin-bottom: 2rem;">
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
                <label for="amount">Amount</label>
                <input type="number" id="amount" name="amount" min="0" required>
            </div>
            <div class="form-group">
                <label for="reason">Reason</label>
                <textarea id="reason" name="reason" rows="3" required></textarea>
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <input type="date" id="date" name="date">
            </div>
            <button type="submit" class="btn btn-primary">Create Deduction</button>
        </form>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">All Deductions</h2>
        <c:if test="${not empty deductions}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Worker</th>
                        <th>Amount</th>
                        <th>Reason</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="deduction" items="${deductions}">
                        <tr>
                            <td>${deduction.workerName}</td>
                            <td>${deduction.amount}</td>
                            <td>${deduction.reason}</td>
                            <td><fmt:formatDate value="${deduction.date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty deductions}">
            <p>No deductions found.</p>
        </c:if>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />
