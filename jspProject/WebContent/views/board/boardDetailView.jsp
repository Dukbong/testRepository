<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.Attachment, com.kh.board.model.vo.Board"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board 상세보기</title>
</head>
<body>

    <%@ include file="../common/menubar.jsp" %>
    
    <%
    	Board b = (Board)request.getAttribute("board");
   		Attachment at = (Attachment)request.getAttribute("at");
    %>
    
    <div class="outer">
	<h2>일반 게시판 상세보기</h2>
    <br>
		<table align="center" border="1px solid white">
			<tr>
				<td width="80">카테고리</td>
				<td width="200"><%=b.getCategoryNo() %></td>
				<td width="80">제목</td>
				<td width="200"><%=b.getBoardTitle() %></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><%=b.getBoardWriter() %></td>
				<td>작성일</td>
				<td><%=b.getCreateDate() %></td>
			</tr>
			<tr>
				<td height= "300">내용</td>
				<td colspan="3">
					<p>
						<%=b.getboardContent()%>
					</p>
				</td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td colspan="3">
					<%if(at != null){ %> <a
					href="<%=contextPath %>/<%=at.getFilePath()%>/<%=at.getChangeName() %>"
					class="btn btn-info" download><%=at.getOriginName() %></a> <%}else{ %> 첨부파일이 없습니다. <%} %>
				</td>
			</tr>
		</table>
		<div align="center">
			<%if(loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())){ %>
				<button type="button" onclick = "location.href='<%=contextPath %>/update.do?bn=<%=request.getParameter("bn") %>'" class="btn btn-info">수정하기</button>
				<button type="button" onclick = "location.href='<%=contextPath %>/delete.do?bn=<%=request.getParameter("bn") %>&changeName=<%=at.getChangeName() %>'" class="btn btn-danger">삭제하기</button>
			<%}%>
			<button type="button" onclick="history.back();" class="btn btn-warning">뒤로가기</button>
		</div>
	</div>
</body>
</html>