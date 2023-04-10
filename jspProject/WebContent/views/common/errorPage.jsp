<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String msg = (String)request.getAttribute("errorMsg");
	%>
	<h1 align="center" style="color:red;"><%=msg %></h1>
	<br><br>
	<h3 align="center"><a href="/jsp">메인 화면으로</a></h3>
</body>
</html>