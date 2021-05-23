<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Trigonometric values</title></head>
<style>
		<c:choose>
			<c:when test="${sessionScope.pickedBgCol != null}">
				body {background-color: #${sessionScope.pickedBgCol}}
			</c:when>
			<c:otherwise>
				body {background-color: #FFFFFF}
			</c:otherwise>
		</c:choose>
</style>
<body>
	<table cols="2" border="1" style="float: left">
		<caption><b>Sin trigonometric values</b></caption>
		<tr><th>Angle (in degrees)</th><th>Value</th></tr>
		<c:forEach var="sinValue" items="${sinValues}">
			<tr><td>${sinValue.key}</td><td>${sinValue.value}</td></tr>
		</c:forEach>
	</table>
	<table cols="2" border="1" style="float: left">
		<caption><b>Cos trigonometric values</b></caption>
		<tr><th>Angle (in degrees)</th><th>Value</th></tr>
		<c:forEach var="cosValue" items="${cosValues}">
			<tr><td>${cosValue.key}</td><td>${cosValue.value}</td></tr>
		</c:forEach>
	</table>
</body>
</html>