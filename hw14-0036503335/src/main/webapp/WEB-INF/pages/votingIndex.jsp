<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>${poll.getTitle()}</title></head>
<body>
	<h1>${poll.getTitle()}:</h1>
	<p>${poll.getMessage()}</p>
	<ol>
	<c:forEach var="pollOption" items="${requestScope.pollOptions}">
		<li><a href="/voting-app/servlets/voting-vote?pollOptionID=${pollOption.getId()}">${pollOption.getOptionTitle()}</a></li>
	</c:forEach>
	</ol>
</body>
</html>