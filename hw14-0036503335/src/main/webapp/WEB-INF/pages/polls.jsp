<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Available polls</title></head>
<body>
	<h1>Currently available polls</h1>
	<p>
	<c:forEach var="poll" items="${requestScope.polls}">
		<li><a href="/voting-app/servlets/voting?pollID=${poll.getId()}">${poll.getTitle()}</a></li>
	</c:forEach>
	</p>
	<p>
	<h2>Results for available polls</h2>
	<c:forEach var="poll" items="${requestScope.polls}">
		Result for:<br>
		<li><a href="/voting-app/servlets/voting-results?pollID=${poll.getId()}">${poll.getTitle()}</a></li>
	</c:forEach>
	</p>
</body>
</html>