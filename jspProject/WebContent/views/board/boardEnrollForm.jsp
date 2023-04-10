<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.kh.board.model.vo.Category"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
		ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("categoryList"); 
	%>
	<div class="outer">
		<h2 align="center">글작성 페이지</h2>
		<!-- 카테고리, 제목, 내용, 첨부파일, 작성자 번호 -->
		<form action="<%=contextPath %>/insert.do" method="post" id="enroll-form" enctype="multipart/form-data">
			<input type="hidden" name="userNo" value="<%=loginUser.getUesrNo()%>">
			<!-- 카테고리 테이블에서 조회해온 카테고리 목록 선택상자 만들기 -->
			<table align="center">
				<tr>
					<th width="100">카테고리</th>
					<td width="500">
						<select name="category">
							<%for(Category c : list){ %>
							<option value="<%=c.getCategoryNo() %>"><%=c.getCategoryName() %></option>
							<%} %>
						</select>
					</td>
				</tr>
				<tr>
					<td>제목</td>
					<td><input type="text" name="title" required></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea name="content" cols="30" rows="10" required></textarea></td>
				</tr>
				<tr>
					<td>첨부파일</td>
					<td><input type="file" name="upfile" ></td>
				</tr>
			</table>
			<br>
			<div align="center">
				<button type="submit">등록하기</button>
				<button type="reset">초기화</button>
			</div>
		</form>
	</div> 
</body>
</html>