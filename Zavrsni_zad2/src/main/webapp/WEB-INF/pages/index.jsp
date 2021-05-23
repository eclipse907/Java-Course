<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Home page</title></head>
<body>
	<form action="drawing" method="post">
		<c:if test="${form.hasError('error')}">
			<br><font class="error"><c:out value="${form.getError('error')}"></c:out></font>
		</c:if><br><br>
		<label for="text">Text:</label><br>
		<textarea name="text" rows="30" cols="30">${form.getText()}</textarea>
		<input type="submit" value="Draw">
	</form>
</body>
</html>