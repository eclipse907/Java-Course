<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>${author}'s blog entries</title></head>
<body>
	<c:if test="${sessionScope['current.user.id'] != null}">
		<p>
		Hello <c:out value="${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}"></c:out>.<br>
		<a href="/blog/servlets/logout">Logout</a>
		</p>
	</c:if>
	<h1>${author}'s blog entries</h1>
	<p>		
	<c:forEach var="blogEntry" items="${userEntries}">
		<a href="/blog/servlets/author/${author}/${blogEntry.getId()}">${blogEntry.getTitle()}</a><br>
	</c:forEach>
	</p>
	<c:if test="${sessionScope['current.user.id'] != null}">
		<c:if test="${sessionScope['current.user.nick'].equals(author)}">
			<p><a href="/blog/servlets/author/${author}/new">Add new blog entry</a></p>
		</c:if>
	</c:if>
	<p><a href="/blog/servlets/main">Return to homepage</a></p>
</body>
</html>