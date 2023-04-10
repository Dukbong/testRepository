<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Page</title>
<style>
.outer {
	background-color: black;
	color: white;
	width: 1000px;
	margin: auto;
	margin-top: 50px;
}

#myPage-form table {
	margin: auto;
}

#myPage-form input {
	margin: 5px;
}

#changPwd, #deleteMember{
	color:black;
}
</style>
</head>
<body>
	<!-- myPage.me -->
	<%@ include file="../common/menubar.jsp"%>

	<%
		String id = loginUser.getUserId();
	String name = loginUser.getUserName();

	String phone = loginUser.getPhone() == null ? "" : loginUser.getPhone();
	String email = loginUser.getEmail() == null ? "" : loginUser.getEmail();
	String address = loginUser.getAddress() == null ? "" : loginUser.getAddress();
	String interest = loginUser.getInterest() == null ? "" : loginUser.getInterest();
	%>
	<div class="outer">
		<br>
		<h2 align="center">마이페이지</h2>
		<form action="<%=contextPath%>/update.me" method="post"
			id="myPage-form">
			<table>
				<tr>
					<td>* 아이디</td>
					<td><input type="text" name="userId" maxlength="12"
						value="<%=id%>" readonly required></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" name="userName" value="<%=name%>"
						required></td>
					<td></td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td><input type="text" name="phone" value="<%=phone%>"
						placeholder="-포함해서 입력"></td>
					<td></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="email" name="email" value="<%=email%>"></td>
					<td></td>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="text" name="address" value="<%=address%>"></td>
					<td></td>
				</tr>
				<tr>
					<td>관심분야</td>
					<td colspan="2"><input type="checkbox" name="interest"
						id="sports" value="운동"> <label for="sports">은동</label> <input
						type="checkbox" name="interest" id="movies" value="영화감상">
						<label for="movies">영화감상</label> <input type="checkbox"
						name="interest" id="board" value="보드타기"> <label
						for="board">보드타기</label><input type="checkbox" name="interest"
						id="food" value="요리"> <label for="food">요리</label><input
						type="checkbox" name="interest" id="game" value="게임"> <label
						for="game">게임</label><input type="checkbox" name="interest"
						id="book" value="독서"><label for="book">독서</label></td>
				</tr>
			</table>

			<script>
				$(function(){
					var interest = "<%=interest%>";
					// 해당요소 순차적으로 접근하기
					if (interest != "null") {
						$("input[type=checkbox]").each(function() {
							if (interest.indexOf($(this).val()) != -1) { // 그냥 제일 쉽게 떠오르는 방법
								$(this).attr("checked", true);
							}

							if (interest.search($(this).val()) != -1) { // 정규 표현식 방법
								$(this).checked = true;
							}
						});
					}
				});
			</script>
			<br> <br>
			<div align="center">
				<button type="submit" class = "btn btn-info">정보변경</button>
				<button type="button" class = "btn btn-warning" data-toggle="modal" data-target="#changPwd">비밀번호 변경</button>
				<button type="button" class = "btn btn-danger" data-toggle="modal" data-target="#deleteMember">회원 탈퇴</button>
			</div>
		</form>
		
		<!-- modal 부분입니다. 비밀번호 모달영역 -->
		<div class="container">
			<!-- The Modal -->
			<div class="modal" id="changPwd">
				<div class="modal-dialog">
					<div class="modal-content">

						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">비밀번호 변경</h4>
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>

						<!-- Modal body -->
						<div class="modal-body" align = "center">
						
							<form action = "<%=contextPath %>/updatePwd.me" method="post">
							<!-- 폼태그 안에 데이터 숨겨서 보내기 -->
							<!-- <input type="hidden" name ="userId" value = "<%=id %>"> -->
							<table>
								<tr>
									<td>현재 비밀번호</td>
									<td><input type="password" name="userpwd" required></td>
								</tr>
								<tr>
									<td>변경할 비밀번호</td>
									<td><input type="password" name="updatePwd" required></td>
								</tr>
								<tr>
									<td>변경할 비밀번호 확인</td>
									<td><input type="password" id="checkpwd" required></td>
								</tr>
							</table>
							<br>
							<button class = "btn btn-info" type="submit" onclick="return validate();">비밀번호 변경</button>
							</form>
							<script>
								function validate(){
									var userPwd = "<%=loginUser.getUserPwd()%>";
									
									var inputPwd = $("input[name=userpwd]").val();
									var updatePwd = $("input[name=updatePwd]").val();
									var checkPwd = $("#checkpwd").val();
									
									if(userPwd == inputPwd){
										if(updatePwd != checkPwd){
											alert("변경할 비밀번호와 확인이 일치하지 않습니다.");
											$("input[name=updatePwd]").select();
											return false;
										}
									}else{
										alert("현재 비밀번호가 일치하지 않습니다.");
										$("input[name=userPwd]").focus();
										return false;
									}
								}
							</script>
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
						</div>

					</div>
				</div>
			</div>

		</div>
		<!-- 회원 탈퇴 모달 -->
		<div class="container">
			<!-- The Modal -->
			<div class="modal" id="deleteMember">
				<div class="modal-dialog">
					<div class="modal-content">

						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">회원 탈퇴</h4>
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>

						<!-- Modal body -->
						<form action="<%=contextPath %>/deleteMember.me" method="post">
						<div class="modal-body" align="center">
							<table>
								<tr>
									<td>
										회원 탈퇴 하시겠습니까?
									</td>
								</tr>
							</table>
								<button type="submit" class="btn btn-danger">확인</button>
								<button type="button" onclick = "gohome();" class="btn btn-danger">취소</button>
						</div>
							<script>
								function gohome(){
									location.href =  "<%=contextPath %>/myPage.me";
								}
							</script>
						</form>

						<!-- Modal footer -->
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
						</div>

					</div>
				</div>
			</div>

		</div>
		<br> <br> <br>
	</div>
</body>
</html>