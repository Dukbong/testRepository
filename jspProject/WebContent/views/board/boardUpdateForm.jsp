<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.Attachment, com.kh.board.model.vo.Board, java.util.*, com.kh.board.model.vo.Category"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board 수정페이지</title>
<style>
	#enroll-form>table{
		border : 1px solid white;
	}
	#enroll-form input, textarea{
		width: 100%;
		box-sizing: border-box;
	}
</style>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	
    <%
    	Board b = (Board)request.getAttribute("board");
   		Attachment at = (Attachment)request.getAttribute("at");
   		ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("categoryList"); 
    %>
	<div class="outer">
		<h2 align="center">글수정 페이지</h2>
		<!-- 카테고리, 제목, 내용, 첨부파일, 작성자 번호 -->
		<form action="<%=contextPath %>/update.do?bn=<%=request.getParameter("bn") %>" method="post" id="enroll-form" enctype="multipart/form-data">
			<input type="hidden" name="userNo" value="<%=loginUser.getUesrNo()%>">
			<input type="hidden" name="fileNo" value="<%=at.getFileNo()%>">
			<input type="hidden" name="OriginFileName" value="<%=at.getOriginName()%>">
			<!-- 카테고리 테이블에서 조회해온 카테고리 목록 선택상자 만들기 -->
			<table align="center">
				<tr>
					<th width="100">카테고리</th>
					<td width="500">
						<select name="category">
							<%for(Category c : list){ %>
								<%if(c.getCategoryName().equals(b.getCategoryNo())) {%>
									<option value="<%=c.getCategoryNo() %>" selected><%=c.getCategoryName()%></option>
								<%} else { %>
									<option value="<%=c.getCategoryNo() %>"><%=c.getCategoryName() %></option>
								<%} %>
							<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<td>제목</td>
					<td><input type="text" name="title" value="<%=b.getBoardTitle() %>" required></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea name="content" cols="30" rows="10" required><%=b.getboardContent() %></textarea></td>
				</tr>
				<tr>					
					<td>수정파일</td>
						<td><input type="file" name="upfile"></td>
				</tr>
					<%if(at != null){ %>
					<tr>
						<td>원본파일</td>
						<td><a name="ori" href="<%=contextPath %>/<%=at.getFilePath()%>/<%=at.getChangeName()%>"><%=at.getOriginName() %></a></td>
					<%} %>					
					</tr>
			</table>
			<br>
			<div align="center">
				<button type="submit">수정한거 등록하기</button>
				<button type="button" onclick="history.back();">뒤로가기</button>
			</div>
		</form>
	</div> 
</body>
</html>