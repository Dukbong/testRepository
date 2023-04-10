<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
</head>
<body>
	<%@ include file = "views/common/menubar.jsp" %>
	<%
	System.out.println(request.getSession().getServletContext().getContextPath());
	%>
	<!-- 
		C R U D
		Create(insert) Read(select) Update Delete
		-- 회원 서비스 
		로그인 : Read(Select)
		회원가입 : Create (Insert)
		정보변경 : Update
		회원탈퇴 : Update OR Delete
		마이페이지 : Read(Select)
		
		-- 게시판
		게시글 조회 : Read(Select)
		게시글 작성 : Create(Insert)
		게시글 수정 : Update
		게시글 삭제 : Update OR Delete
		
		-- 댓글
		댓글 조회 : Read(Select)
		댓글 작성 : Create(Insert)
		댓글 수정 : Update
		댓글 삭제 : Update OR Delete
	 -->
	 <ul>
	<li onclick="kakaoLogin();">
      <a href="javascript:void(0)">
          <span>카카오 로그인</span>
      </a>
	</li>
	<li onclick="kakaoLogout();">
      <a href="javascript:void(0)">
          <span>카카오 로그아웃</span>
      </a>
	</li>
</ul>

<form name="form" action="/jsp/test" method="post">
<input type="hidden" name="userName" id="testn" value = "">
</form>

<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script>
Kakao.init('f91f4c1499628ccd44bb5d41070cb9a1'); //발급받은 키 중 javascript키를 사용해준다.
console.log(Kakao.isInitialized()); // sdk초기화여부판단
//카카오로그인
function kakaoLogin() {
    Kakao.Auth.login({
      success: function (response) {
        Kakao.API.request({
          url: '/v2/user/me',
          success: function (response) {
        	  console.log("this is !!>>");
        	  console.log(response);
        	  console.log(Object.keys(response));
        	  console.log(response.kakao_account);
        	  console.log(response.kakao_account.email);
        	  // 카카오톡 정보 가져오기 >> 로그인 구현 귀찮아서 패쓰.....
        	  //document.getElementById("content").innerHTML = "회원 email = " + response.kakao_account.email + "\n회원의 gender = " + response.kakao_account.gender + "\n회원의 닉네임 = " + response.kakao_account.profile.nickname;
            document.getElementById("testn").value =  response.kakao_account.profile.nickname; // input에 벨류 집어넣고
            document.form.submit(); // document.form_Name.submit() 하면 자동 submit이 된다.
            //location.href = "/jsp/test";
          },
          fail: function (error) {
            console.log(error)
          },
        })
      },
      fail: function (error) {
        console.log(error)
      },
    })
  }
//카카오로그아웃  
function kakaoLogout() {
    if (Kakao.Auth.getAccessToken()) {
      Kakao.API.request({
        url: '/v1/user/unlink',
        success: function (response) {
        	console.log(response)
        },
        fail: function (error) {
          console.log(error)
        },
      })
      Kakao.Auth.setAccessToken(undefined)
    }
  }  
</script>
</body>
</html>