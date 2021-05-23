<%@ page import="java.util.concurrent.ThreadLocalRandom" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%! private static String[] textColors = {"#FFC0CB", "#FFFF00", "#0000FF", "#000000", "#FF7111", "#FBD082"}; %>
<html>
<head><title>Funny story</title></head>
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
	<h1>Turn it off and on again</h1>
	<font color="<%= textColors[ThreadLocalRandom.current().nextInt(0, textColors.length)] %>">
	 Whoever said that the definition of insanity is doing the same thing over and over again<br>
	 and expecting different results has obviously never had to reboot a computer.
	</font>
</body>
</html>