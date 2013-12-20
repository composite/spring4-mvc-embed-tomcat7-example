<!doctype html><%@page contentType="text/html" pageEncoding="UTF-8" %>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Spring View</title>
</head>
<body>
	<h1>Hello world!</h1>
	<h2>Using JSTL Date : ${date }</h2>
	<h2>Using plain JSP Date : <%=request.getAttribute("date") %></h2>
</body>
</html>

