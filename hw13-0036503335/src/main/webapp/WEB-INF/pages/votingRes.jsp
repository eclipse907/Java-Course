<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Voting results</title>
	<style type="text/css">
		table.rez td {text-align: center;}
		<c:choose>
			<c:when test="${sessionScope.pickedBgCol != null}">
				body {background-color: #${sessionScope.pickedBgCol}}
			</c:when>
			<c:otherwise>
				body {background-color: #FFFFFF}
			</c:otherwise>
		</c:choose>
	</style>
</head>
<body>
	<h1>Voting results</h1>
 	<p>These are the voting results.</p>
 	<table border="1" cellspacing="0" class="rez">
 	<thead><tr><th>Band</th><th>Number of votes</th></tr></thead>
 	<tbody>
 	<c:forEach var="result" items="${requestScope.results}">
		<tr><td>${result[1]}</td><td>${result[2]}</td></tr>
	</c:forEach>
	</tbody>
	</table>

 	<h2>Pie chart of the voting results</h2>
 	<c:choose>
		<c:when test="${requestScope.winnersSongs != null}">
			<img alt="Pie-chart" src="/webapp2/voting-graphics" width="400" height="400" />
		</c:when>
		<c:otherwise>
			No one has voted yet.
		</c:otherwise>
	</c:choose>

	<h2>Results in XLS format</h2>
	<p>Results in XLS format are available <a href="/webapp2/voting-xls">here</a></p>

 	<h2>Various</h2>
 	<p>Examples of songs from the wining bands:</p>
 	<ul>
 	<c:choose>
		<c:when test="${requestScope.winnersSongs != null}">
			<c:forEach var="winnerSong" items="${requestScope.winnersSongs}">
				<li><a href="${winnerSong[1]}" target="_blank">${winnerSong[0]}</a></li>
			</c:forEach>
		</c:when>
		<c:otherwise>
			No one has voted yet.
		</c:otherwise>
	</c:choose>
 	</ul>
</body>
</html>