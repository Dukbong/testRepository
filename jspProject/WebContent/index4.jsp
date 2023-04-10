<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.min.js"></script>
<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
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

<form name="formt" action="/jsp/test" method="post">
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
        	  // 카카오톡 정보 가져오기
        	  //document.getElementById("content").innerHTML = "회원 email = " + response.kakao_account.email + "\n회원의 gender = " + response.kakao_account.gender + "\n회원의 닉네임 = " + response.kakao_account.profile.nickname;
            document.getElementById("testn").value =  response.kakao_account.profile.nickname; // input에 벨류 집어넣고
            document.formt.submit(); // document.form_Name.submit() 하면 자동 submit이 된다.
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