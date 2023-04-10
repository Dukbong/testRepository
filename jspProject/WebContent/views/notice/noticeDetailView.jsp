<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.notice.model.vo.Notice, java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#detail-area{
		border : 1px solid white;
	}

</style>
</head>
<body>
	<%
		String title = ((Notice)request.getAttribute("notice")).getNoticeTitle();
		String content = ((Notice)request.getAttribute("notice")).getNoticeContent();
		Date date = ((Notice)request.getAttribute("notice")).getCreateDate();
		String id = ((Notice)request.getAttribute("notice")).getNoticeWriter();
		int no = (int)request.getAttribute("noticeNo");
	%>
	<%@include file="../common/menubar.jsp" %>
	<div class="outer">
		<br><br>
		<h2 align="center">공지사항 상세보기</h2>
		
		<table id="detail-area" align="center">
			<tr>
				<th width="70">제목</th>
				<td width="350" colspan="3"><%=title %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=id %></td>
				<th>작성일</th>
				<td><%=date %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3"><p style="height:150px"><%=content %></p></td>
			</tr>
		</table>
		<br><br>
		<%if(loginUser != null && (loginUser.getUserId().equals(id) || loginUser.getUserId().equals("admin"))){ %>
		<div align="center">
			<a href="<%=contextPath %>/update.no?no=<%=no %>"  class="btn btn-warning">수정하기</a>
			<a href="<%=contextPath %>/delete.no?no=<%=no %>" class="btn btn-danger">삭제하기</a>
		</div>
		<%} %>
		
		
	</div>
	
	
</body>
</html>