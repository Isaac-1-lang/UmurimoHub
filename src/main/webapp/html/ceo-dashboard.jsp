<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="CEO Dashboard" />
</jsp:include>

<div class="container">
    <jsp:include page="../includes/sidebar-ceo.jsp" />
    
    <div class="main-content">
        <h1 class="page-title">CEO Dashboard</h1>

        <c:if test="${not empty report}">
            <div class="stats-grid">
                <div class="stat-card">
                    <h3>Total Workers</h3>
                    <div class="value">${report.totalWorkers}</div>
                </div>
                <div class="stat-card">
                    <h3>Active Workers</h3>
                    <div class="value">${report.activeWorkers}</div>
                </div>
                <div class="stat-card">
                    <h3>Total HR Users</h3>
                    <div class="value">${report.totalHR}</div>
                </div>
                <div class="stat-card">
                    <h3>Total Attendance Records</h3>
                    <div class="value">${report.totalAttendance}</div>
                </div>
                <div class="stat-card">
                    <h3>Total Deductions</h3>
                    <div class="value">${report.totalDeductionCount}</div>
                </div>
                <div class="stat-card">
                    <h3>Total Deduction Amount</h3>
                    <div class="value">${report.totalDeductionAmount}</div>
                </div>
            </div>
        </c:if>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">Recent HR Activities</h2>
        <c:if test="${not empty hrActivities}">
            <table class="table">
                <thead>
                    <tr>
                        <th>HR Name</th>
                        <th>Action</th>
                        <th>Timestamp</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="activity" items="${hrActivities}" begin="0" end="9">
                        <tr>
                            <td>${activity.hrName}</td>
                            <td>${activity.action}</td>
                            <td>${activity.timestamp}</td>
                            <td>${activity.details}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />
