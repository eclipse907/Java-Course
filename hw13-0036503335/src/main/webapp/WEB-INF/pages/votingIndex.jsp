<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Vote for your favorite band</title></head>
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
	<h1>Vote for your favorite band:</h1>
	<p>From the given bands, which one is your favorite? Click on the link to vote!</p>
	<ol>
	<c:forEach var="band" items="${requestScope.bands}">
		<li><a href="/webapp2/voting-vote?id=${band.key}">${band.value[0]}</a></li>
	</c:forEach>
	</ol>
</body>
</html>