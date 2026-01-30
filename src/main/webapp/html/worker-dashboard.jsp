<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Worker Dashboard" />
</jsp:include>

<div class="container">
    <jsp:include page="../includes/sidebar-worker.jsp" />
    
    <div class="main-content">
        <h1 class="page-title">My Profile</h1>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">My Attendance</h2>
        <c:if test="${not empty attendances}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Remarks</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="attendance" items="${attendances}">
                        <tr>
                            <td><fmt:formatDate value="${attendance.date}" pattern="yyyy-MM-dd" /></td>
                            <td>${attendance.status}</td>
                            <td>${attendance.remarks}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty attendances}">
            <p>No attendance records found.</p>
        </c:if>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">My Deductions</h2>
        <c:if test="${not empty deductions}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Amount</th>
                        <th>Reason</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="deduction" items="${deductions}">
                        <tr>
                            <td><fmt:formatDate value="${deduction.date}" pattern="yyyy-MM-dd" /></td>
                            <td>${deduction.amount}</td>
                            <td>${deduction.reason}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <p style="margin-top: 1rem;"><strong>Total Deductions: ${totalDeductions}</strong></p>
        </c:if>
        <c:if test="${empty deductions}">
            <p>No deductions found.</p>
        </c:if>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">My Disciplinary Actions</h2>
        <c:if test="${not empty punishments}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Title</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="punishment" items="${punishments}">
                        <tr>
                            <td><fmt:formatDate value="${punishment.date}" pattern="yyyy-MM-dd" /></td>
                            <td>${punishment.title}</td>
                            <td>${punishment.description}</td>
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
