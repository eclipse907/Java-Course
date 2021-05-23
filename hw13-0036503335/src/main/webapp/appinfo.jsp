<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!
private String convertToDate(long milliseconds) {
	long millis = milliseconds % 1000;
	long seconds = (milliseconds / 1000) % 60;
	long minutes = (milliseconds / (1000 * 60)) % 60;
	long hours = (milliseconds / (1000 * 60 * 60)) % 24;
	long days = (milliseconds / (1000 * 60 * 60 * 24));
	StringBuilder timeElapsed = new StringBuilder();
	if (days > 0) {
		timeElapsed.append(Long.toString(days) + " days ");
	}
	if (hours > 0) {
		timeElapsed.append(Long.toString(hours) + " hours ");
	}
	if (minutes > 0) {
		timeElapsed.append(Long.toString(minutes) + " minutes ");
	}
	if (seconds > 0) {
		timeElapsed.append(Long.toString(seconds) + " seconds ");
	}
	if (millis > 0) {
		timeElapsed.append(Long.toString(millis) + " milliseconds");
	}
	return timeElapsed.toString().trim() + ".";
}
%>
<html>
<head><title>App info</title></head>
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
	<h1>Time elapsed since web app startup</h1>
	<p><%= convertToDate(System.currentTimeMillis() - (Long)application.getAttribute("startTime")) %></p>
</body>
</html>