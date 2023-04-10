<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <style>
            #enroll-form {
                border: 1px solid white;
            }

            #enroll-form input,
            textarea {
                width: 100%;
                box-sizing: border-box;
            }
        </style>
    </head>

    <body>
        <%@ include file="../common/menubar.jsp" %>
            <div class="outer">
                <br>
                <h2 align="center">사진 게시글 작성하기</h2>
                <br>

                <form action="<%=contextPath %>/insert.ph" method="post" id="enroll-form" enctype="multipart/form-data">
                    <input type="hidden" name="userNo" value="<%=loginUser.getUesrNo() %>">
                    <table align="center">
                        <tr>
                            <th width="100">제목</th>
                            <td colspan="3"><input type="text" name="title" required></td>
                        </tr>
                        <tr>
                            <th>내용</th>
                            <td colspan="3"><textarea name="content" cols="30" rows="10" required
                                    style="resize:none;"></textarea></td>
                        </tr>
                        <tr>
                            <th>썸네일</th>
                            <td colspan="3" align="center">
                                <img src="" width="250" height="170" id="titleImg">
                            </td>
                        </tr>
                        <tr>
                            <th>상세 이미지</th>
                            <td><img src="" id="contentImg1" width="150" height="120"></td>
                            <td><img src="" id="contentImg2" width="150" height="120"></td>
                            <td><img src="" id="contentImg3" width="150" height="120"></td>
                        </tr>
                    </table>

                    <!-- 파일 첨부 영역 -->
                    <!-- 1. 대표이미지 영역 -->
                    <div id="file-area" align="center">
                        <input type="file" id="file1" name="file1" onchange="loadImg(this,1);" required>
                        <input type="file" id="file2" name="file2" onchange="loadImg(this,2);">
                        <input type="file" id="file3" name="file3" onchange="loadImg(this,3);">
                        <input type="file" id="file4" name="file4" onchange="loadImg(this,4);">
                    </div>
                    <br><br>

                    <div align="center">
                        <button type="submit">작성하기</button>
                    </div>

                </form>
            </div>
            <script>
                $(function () {
                    $("#file-area").hide(); // 숨긴다.

                    $("#titleImg").click(function () {
                        $("#file1").click();
                    })
                    $("#contentImg1").click(function () {
                        $("#file2").click();
                    })
                    $("#contentImg2").click(function () {
                        $("#file3").click();
                    })
                    $("#contentImg3").click(function () {
                        $("#file4").click();
                    })
                })
                function loadImg(input, num) {
                    // console.log(input.files); // input type file인 것을 배열로 만든다. (첨부 되면 배열길이 1 아니면 0)
                    // 업로드 된 파일의 정보를 배열로 반환하는 속성이다.
                    if(input.files.length == 1){
                        // 선택한 파일이 존재한다면
                        // 해당 파일을 읽어서 해당 영역에 미리보기 시켜주기
                        // 파일을 읽어주는 객체 : FileReader
                        var reader = new FileReader();
                        // 파일을 읽어줄 메서드 : readasDataUrl(파일)
                        // -- 파일을 읽어들이는 순서 고유한 url을 부여한다.
                        // -- 부여한 url을 src에 추가하면 해당 이미지를 띄워줄수 있다.
                        reader.readAsDataURL(input.files[0]);
                        
                        // 파일 읽기가 완료된 시점에 img src속성에 해당 url 담아주기 작업
                        reader.onload = function(e){ // window.onload 처럼이다. // e는 이벤트 객체를 의미한다.
                            //console.log(e);
                            //console.log(e.target);
                            //console.log(e.target.result);
                            
                            // console.log($("input").attr("src", e.target.result));
                            //$("input").eq(num+1).attr("src", e.target.result);
                            
                            switch(num){
                            case 1: $("#titleImg").attr("src", e.target.result);
                            	break;
                            case 2: $("#contentImg1").attr("src", e.target.result);
                            	break;
                            case 3: $("#contentImg2").attr("src", e.target.result);
                            	break;
                            case 4: $("#contentImg3").attr("src", e.target.result);
                            	break;
                            }
                        }
                    }else{
                    	switch(num){
                        case 1: $("#titleImg").attr("src", null);
                        	break;
                        case 2: $("#contentImg1").attr("src", null);
                        	break;
                        case 3: $("#contentImg2").attr("src", null);
                        	break;
                        case 4: $("#contentImg3").attr("src", null);
                        	break;
                        }
                    }
                }
            </script>
    </body>

    </html>