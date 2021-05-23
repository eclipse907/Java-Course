<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Choose color</title></head>
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
	<p>Select color</p>
		<a href="/webapp2/setcolor?color=FFFFFF">WHITE</a><br>
		<a href="/webapp2/setcolor?color=FF0000">RED</a><br>
		<a href="/webapp2/setcolor?color=008000">GREEN</a><br>
		<a href="/webapp2/setcolor?color=00FFFF">CYAN</a>
</body>
</html>