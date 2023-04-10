<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.kh.board.model.vo.Board, com.kh.common.PageInfo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board List View</title>
<style>
	.list-area{
		border: 1px solidㄱ white;
		text-align: center;
	}
	.list-area>tbody>tr:hover{
		background-color: gray;
		cursor: pointer;
	}
</style>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	<%
		ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	%>
	<div class="outer">
		<br>
		<h1>일반 게시판</h1>
		<br><br>
		<%if(loginUser != null){ %>
		<div align="right" style="margin-right: 15px;">
			<a href="<%=contextPath%>/insert.do" class="btn btn-danger">글 작성</a>
		</div>
		<%} %>
		<br>
		<table align="center" class="list-area">
			<thead>
				<tr>
					<th width="70">글번호</th>
					<th width="70">카테고리</th>
					<th width="300">제목</th>
					<th width="100">작성자</th>
					<th width="50">조회수</th>
					<th width="100">작성일</th>
				</tr>
			</thead>
			<tbody>
				<%if(list.isEmpty()){ %>
					<tr>
						<td colspan="6">조회된 게시글이 없습니다.</td>
					</tr>
				<%} else { %>
					<% for(int i = 0; i < list.size(); i++){ %>
						<tr>
							<td><%=list.get(i).getBoardNo() %></td>
							<td><%=list.get(i).getCategoryNo() %></td>
							<td><%=list.get(i).getBoardTitle() %></td>
							<td><%=list.get(i).getBoardWriter() %></td>
							<td><%=list.get(i).getCount() %></td>
							<td><%=list.get(i).getCreateDate() %></td>
						</tr>
					<% }}%>
			</tbody>
		</table>
		<br><br>
		<div align="center" class = "paging-area">
		<%if(pi.getCurrentPage() != 1){ %>
		<% int pre = Integer.parseInt(request.getParameter("currentPage")) - 1; %>
			<button onclick = "location.href = '<%=contextPath %>/list.do?currentPage=<%=pre %>'">&lt;</button>
		<%}%>
		<%for (int i = pi.getStartPage(); i <= pi.getEndPage(); i++) {%>
			<%if(i != pi.getCurrentPage()){ %>
				<button class="btn btn-info" onclick="location.href='<%=contextPath %>/list.do?currentPage=<%=i%>'"><%=i %></button>
			<%} else { %>
				<button class="btn btn-danger" disabled><%=i %></button>
			<%} %>
		<%} %>
		
		<%if(pi.getCurrentPage() != pi.getMaxPage()){ %>
			<% int next = Integer.parseInt(request.getParameter("currentPage")) + 1; %>
				<button onclick="location.href = '<%=contextPath %>/list.do?currentPage=<%=next %>'">&gt;</button>
		<%} %>
		</div>
	</div>
	<script>
		$(function(){
			$("tbody").on("click","tr", function(){
				var no = $(this).children().eq(0).text();
				location.href = "<%=contextPath %>/detail.do?bn="+no;
			})
		})
	</script>
</body>
</html>