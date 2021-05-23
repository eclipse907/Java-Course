<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>
		${blogEntryMode} blog entry
	</title>
</head>
<body>
	<p>
	Hello <c:out value="${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}"></c:out>.<br>
	<a href="/blog/servlets/logout">Logout</a>
	</p>
	<h1>${blogEntryMode} blog entry</h1>
	<p>
	<c:if test="${blogEntryMode.equals('New')}">
		<form action="new" method="post">
			<label for="title">Title:</label><br>
			<input type="text" id="title" name="title" required><br><br>
			<label for="text">Text:</label><br>
			<textarea name="text" rows="30" cols="30"></textarea><br><br>
			<input type="submit" value="Post">
		</form>
	</c:if>
	<c:if test="${blogEntryMode.equals('Edit')}">
		<form action="edit" method="post">
			<label for="title">Title:</label><br>
			<input type="text" id="title" name="title" required value="${blogEntry.getTitle()}"><br><br>
			<label for="text">Text:</label><br>
			<textarea name="text" rows="30" cols="30">${blogEntry.getText()}</textarea><br><br>
			<input type="hidden" id="blogEntryId" name="id" value="${blogEntry.getId()}">
			<input type="submit" value="Save changes">
		</form>
	</c:if>
	</p>
	<p><a href="/blog/servlets/main">Return to homepage</a></p>
</body>
</html>