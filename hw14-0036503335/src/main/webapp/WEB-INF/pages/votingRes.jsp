<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Voting results</title>
	<style type="text/css">
		table.rez td {text-align: center;}
	</style>
</head>
<body>
	<h1>Voting results</h1>
 	<p>These are the voting results.</p>
 	<table border="1" cellspacing="0" class="rez">
 	<thead><tr><th>Poll option title</th><th>Number of votes</th></tr></thead>
 	<tbody>
 	<c:forEach var="pollVotingResult" items="${requestScope.pollVotingResults}">
		<tr><td>${pollVotingResult.getOptionTitle()}</td><td>${pollVotingResult.getVotesCount()}</td></tr>
	</c:forEach>
	</tbody>
	</table>

 	<h2>Pie chart of the poll voting results</h2>
 	<c:choose>
		<c:when test="${requestScope.mostVotedPollOptions.size() > 0}">
			<img alt="Pie-chart" src="/voting-app/servlets/voting-graphics?pollID=${poll.getId()}" width="400" height="400" />
		</c:when>
		<c:otherwise>
			No one has voted in this poll yet.
		</c:otherwise>
	</c:choose>

	<h2>Poll results in XLS format</h2>
	<p>Poll results in XLS format are available <a href="/voting-app/servlets/voting-xls?pollID=${poll.getId()}">here</a></p>

 	<h2>Various</h2>
 	<p>Examples of content from the winning poll options:</p>
 	<ul>
 	<c:choose>
		<c:when test="${requestScope.mostVotedPollOptions.size() > 0}">
			<c:forEach var="mostVotedPollOption" items="${requestScope.mostVotedPollOptions}">
				<li><a href="${mostVotedPollOption.getOptionLink()}" target="_blank">${mostVotedPollOption.getOptionTitle()}</a></li>
			</c:forEach>
		</c:when>
		<c:otherwise>
			No one has voted in this poll yet.
		</c:otherwise>
	</c:choose>
 	</ul>
</body>
</html>