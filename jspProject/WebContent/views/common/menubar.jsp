<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.kh.member.model.vo.Member"%>
	
	
<%
	String contextPath = request.getContextPath();
// context root 가죠오기

// 로그인 유저 가져오기
Member loginUser = (Member) session.getAttribute("loginUser");

// 로그인 되지 않았다면 loginUser는 Null 이다.
// attribute에 해당 하는 키값이 없기때문이다.
// 로그인이 되어있다면 해당 유저 정보가 담겨있다.
String alertMsg = (String) session.getAttribute("alertMsg");
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Menu bar</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<style>
#login-form, #user-info {
	float: right;
}

#user-info a {
	text-decoration: none;
	color: balck;
	font-size: 12px;
}

.nav-area {
	background-color: black;
}

.menu {
	display: table-cell;
	height: 50px;
	width: 150px;
}

.menu a {
	text-decoration: none;
	color: white;
	font-size: 20px;
	font-weight: bold;
	width: 100%;
	height: 100%;
	display: block;
	/* block으로 지정후 width height 100%fmf 주면 꽉차요*/
	line-height: 50px;
	margin-left: 10px;
}

#mypage-form table{
	margin : auto;
}
#mypage-form .modal-content{
	color : black;
}
    .outer{
        background-color: black;
        color: white;
        width: 1000px;
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }
</style>
</head>

<body>
	<script>
		var msg = "<%=alertMsg%>"; // 성공적으로 로그인 되었습니다. or null
		
		
		if(msg != "null"){
			alert(msg);
			// 알람 메시지 지워주기
			// 지우지 않으면 menubar jsp가 열릴때 마다 매번 알람 뜬다.
			<%session.removeAttribute("alertMsg");%>
		}
	
	</script>
	<h1 align="center">Welcome my Web</h1>

	<div class="login-area">
	<%if (loginUser == null){ %>
		<!-- 로그인 전에 보여질 화면 -->
		<form action="/jsp/login.me" method="post" id="login-form">
			<table>
				<tr>
					<td>아이디</td>
					<td><input type="text" name="userId" required ></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="userPwd" required></td>
				</tr>
				<tr>
					<th colspan="2">
						<button type="submit">로그인</button>
						<button type="button" onclick="enrollPage();">회원가입</button>
					</th>
				</tr>
			</table>
		</form>
		<script>
			function enrollPage(){
				// location.href = "<%=contextPath %>/views/member/memberEnrollForm.jsp";
				// 웹 애플리케이션의 디렉토리 구조가 그대로 url에 노출되기 때문에 서블릿을 통해 매핑값이 노출되게 만들어줘야 한다.
				location.href = "<%=contextPath %>/enrollForm.me";
			}
		</script>
		<%}else{ %>
		<!-- 로그인 후에 보여질 화면 -->
		<div id="user-info">
			<b><%=loginUser.getUserId() %></b> 환영합니다. <br>
			<br>
			<div align="center">
				<a href="<%=contextPath%>/myPage.me">마이페이지</a> <a href="<%=contextPath%>/logout.me">로그아웃</a>
			</div>
		</div>
		<%} %>
	</div>

	<br clear="both">

	<div class="nav-area" align="center">
		<div class="menu">
			<a href="<%=contextPath %>">HOME</a>
		</div>
		<div class="menu">
			<a href="<%=contextPath %>/list.no">공지사항</a>
		</div>
		<div class="menu">
			<a href="<%=contextPath %>/list.do?currentPage=1">일반게시판</a>
		</div>
		<div class="menu">
			<a href="<%=contextPath %>/list.ph">사진게시판</a>
		</div>
	</div>

</body>

</html>