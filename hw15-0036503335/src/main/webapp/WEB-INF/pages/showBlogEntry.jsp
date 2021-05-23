<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Blog entry</title></head>
<style type="text/css">
		.error {
		   font-weight: bold;
		   color: #FF0000;
		}
</style>
<body>
	<c:if test="${sessionScope['current.user.id'] != null}">
		<p>
		Hello <c:out value="${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}"></c:out>.<br>
		<a href="/blog/servlets/logout">Logout</a>
		</p>
	</c:if>
	<h1>Blog entry</h1>
	<p><b>${blogEntry.getTitle()}</b></p>
	<p>${blogEntry.getText()}</p>
	<c:if test="${sessionScope['current.user.id'] != null}">
		<c:if test="${sessionScope['current.user.id'] == blogEntry.getAuthor().getId()}">
			<p><a href="/blog/servlets/author/${sessionScope['current.user.nick']}/edit?id=${blogEntry.getId()}">Edit blog entry</a></p>
		</c:if>
	</c:if>
	<p>
	<c:choose>		
		<c:when test="${blogEntry.getComments().size() > 0}">
			<c:forEach var="comment" items="${blogEntry.getComments()}">
				<hr>
				<font id="commentArea">${comment.getMessage()}</font>
				<hr>
				<label for="commentArea">Posted by <b>${comment.getUsersEMail()}</b> on <b>${comment.getPostedOn()}</b></label>
			</c:forEach>
		</c:when>
		<c:otherwise>
			This blog entry has no comments.
		</c:otherwise>
	</c:choose>
	</p>
	<form action="${blogEntry.getId()}" method="post">
		<c:choose>		
			<c:when test="${sessionScope['current.user.id'] != null}">
				<input type="hidden" name="usersEMail" value="${sessionScope['current.user.email']}">
			</c:when>
			<c:otherwise>
				<label for="email">E-mail:</label><br>
				<input type="text" id="email" name="usersEMail" value="${form.getUsersEMail()}">
				<c:if test="${form.hasError('usersEMail')}">
					<br><font class="error"><c:out value="${form.getError('usersEMail')}"></c:out></font>
				</c:if><br><br>
			</c:otherwise>
		</c:choose>
		<label for="message">Message:</label><br>
		<textarea name="message" rows="30" cols="30">${form.getMessage()}</textarea>
		<c:if test="${form.hasError('message')}">
			<br><font class="error"><c:out value="${form.getError('message')}"></c:out></font>
		</c:if><br><br>
		<input type="submit" value="Post comment">
	</form>
	<p><a href="/blog/servlets/main">Return to homepage</a></p>
</body>
</html>