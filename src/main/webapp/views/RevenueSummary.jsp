<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<body>
	<h2>Spring MVC</h2>

	<h3>Revenue Report</h3>

	<table border="1px" cellpadding="8px">
		<tr>
			<td>Month</td>
			<td>Revenue</td>
		</tr>

		<c:if test="${not empty billDetailList}">
			<c:forEach items="${billDetailList}" var="billDetail"
				varStatus="status">
				<c:if test="${status.count== param.billId}">
					<tr>
					   <td><c:out value="${billDetail.billId}" /></td>
					   <td><c:out value="${current.apartmentId}" /></td>
					</tr>
				</c:if>
			</c:forEach>


		</c:if>

	</table>

</body>
</html>