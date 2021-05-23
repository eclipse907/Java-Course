<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Homepage</title>
	<style type="text/css">
		.greska {
		   font-weight: bold;
		   color: #FF0000;
		}
	</style>
</head>
<body>
	<h1>Homepage</h1>
	<c:choose>
		<c:when test="${sessionScope['current.user.id'] == null}">
			<p>Not logged in.</p>
			<form action="main" method="post">
				<label for="nick">Nickname:</label><br>
				<input type="text" id="nick" name="nick" value='<c:out value="${form.nick}"></c:out>'>
				<c:choose>
					<c:when test="${form.hasError('nick')}">
						<br><font class="greska"><c:out value="${form.getError('nick')}"></c:out></font>
					</c:when>
					<c:when test="${nickError != null}">
						<br><font class="greska"><c:out value="${nickError}"></c:out></font>
					</c:when>
				</c:choose><br><br>
				<label for="password">Password:</label><br>
				<input type="password" id="password" name="password" value='<c:out value="${form.password}"></c:out>'>
				<c:choose>
					<c:when test="${form.hasError('password')}">
						<br><font class="greska"><c:out value="${form.getError('password')}"></c:out></font>
					</c:when>
					<c:when test="${passwordError != null}">
						<br><font class="greska"><c:out value="${passwordError}"></c:out></font>
					</c:when>
				</c:choose><br><br>
				<input type="submit" value="Login">
			</form>
			<p><a href="/blog/servlets/register">Register</a></p>
		</c:when>
		<c:otherwise>
		<p>
		 Hello <c:out value="${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}"></c:out>.<br>
		 <a href="/blog/servlets/logout">Logout</a>
		</p>
		</c:otherwise>
	</c:choose>
	<h2>Authors</h2>
	<p>
	<c:forEach var="author" items="${authors}">
		<a href="/blog/servlets/author/${author.getNick()}">${author.getNick()}</a><br>
	</c:forEach>
	</p>
</body>
</html>