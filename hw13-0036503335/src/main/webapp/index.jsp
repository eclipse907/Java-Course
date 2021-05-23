<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Home page</title></head>
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
	<h1>Home page</h1>
		<a href="colors.jsp">Background color chooser</a><br>
		<a href="/webapp2/trigonometric?a=0&b=90">Trigonometric values</a><br>
		<form action="trigonometric" method="GET">
 			Start angle:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 			End angle:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 		<input type="submit" value="Show table"><input type="reset" value="Reset values">
		</form>
		<a href="/webapp2/stories/funny.jsp">Funny story</a><br>
		<a href="report.jsp">OS usage report</a><br>
		<a href="/webapp2/powers?a=1&b=100&n=3">Download excel powers worksheet</a><br>
		<a href="appinfo.jsp">App information</a><br>
	<h2>Favorite band voting</h2>
		<a href="/webapp2/voting">Vote for a favorite band</a><br>
		<a href="/webapp2/voting-results">View the voting results</a>
	
	<a href="test.html">Test</a>
</body>
</html>