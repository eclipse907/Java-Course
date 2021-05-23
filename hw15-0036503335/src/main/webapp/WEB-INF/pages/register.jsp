<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Register</title>
	<style type="text/css">
		.greska {
		   font-weight: bold;
		   color: #FF0000;
		}
	</style>
</head>
<body>
	<h1>Register</h1>
	<form action="register" method="post">
		<label for="firstName">First name:</label><br>
		<input type="text" id="firstName" name="firstName" value='<c:out value="${form.firstName}"></c:out>'>
		<c:if test="${form.hasError('firstName')}">
			<br><font class="greska"><c:out value="${form.getError('firstName')}"></c:out></font>
		</c:if>
		<br>
		<br>
		<label for="lastName">Last name:</label><br>
		<input type="text" id="lastName" name="lastName" value='<c:out value="${form.lastName}"></c:out>'>
		<c:if test="${form.hasError('lastName')}">
			<br><font class="greska"><c:out value="${form.getError('lastName')}"></c:out></font>
		</c:if>
		<br>
		<br>
		<label for="nick">Nickname:</label><br>
		<input type="text" id="nick" name="nick" value='<c:out value="${form.nick}"></c:out>'>
		<c:if test="${form.hasError('nick')}">
			<br><font class="greska"><c:out value="${form.getError('nick')}"></c:out></font>
		</c:if>
		<br>
		<br>
		<label for="email">E-mail:</label><br>
		<input type="text" id="email" name="email" value='<c:out value="${form.email}"></c:out>'>
		<c:if test="${form.hasError('email')}">
			<br><font class="greska"><c:out value="${form.getError('email')}"></c:out></font>
		</c:if>
		<br>
		<br>
		<label for="password">Password:</label><br>
		<input type="password" id="password" name="password" value='<c:out value="${form.password}"></c:out>'>
		<c:if test="${form.hasError('password')}">
			<br><font class="greska"><c:out value="${form.getError('password')}"></c:out></font>
		</c:if>
		<br>
		<br>
		<input type="submit" value="Register">
	</form>
	<p><a href="/blog/servlets/main">Return to homepage</a></p>
</body>
</html>