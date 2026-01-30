<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../includes/header.jsp">
    <jsp:param name="title" value="Attendance Management" />
</jsp:include>

<div class="container">
    <jsp:include page="../includes/sidebar-hr.jsp" />
    
    <div class="main-content">
        <h1 class="page-title">Attendance Management</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">Record Attendance</h2>
        <form action="${pageContext.request.contextPath}/Attendance" method="POST" style="max-width: 600px; margin-bottom: 2rem;">
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
                <label for="date">Date</label>
                <input type="date" id="date" name="date" required>
            </div>
            <div class="form-group">
                <label for="status">Status</label>
                <select id="status" name="status" required>
                    <option value="">Select Status</option>
                    <option value="PRESENT">Present</option>
                    <option value="ABSENT">Absent</option>
                    <option value="LATE">Late</option>
                    <option value="LEAVE">Leave</option>
                </select>
            </div>
            <div class="form-group">
                <label for="remarks">Remarks</label>
                <textarea id="remarks" name="remarks" rows="3"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Record Attendance</button>
        </form>

        <h2 style="margin-top: 2rem; margin-bottom: 1rem;">Attendance Records</h2>
        <c:if test="${not empty attendances}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Worker</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Remarks</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="attendance" items="${attendances}">
                        <tr>
                            <td>${attendance.workerName}</td>
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
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />
