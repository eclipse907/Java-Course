<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Registered</title></head>
<body>
	<h1>Successfully registered</h1>
	<p>
	Thank you <c:out value="${sessionScope['current.user.fn']}"></c:out>.<br>
	You have successfully registered.
	</p>
	<p><a href="/blog/servlets/main">Return to homepage</a></p>
</body>
</html>