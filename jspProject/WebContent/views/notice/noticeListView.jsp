<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.notice.model.vo.Notice"%>
<%
	// 담은 리스트 꺼내주기
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer{
        background-color: black;
        color: white;
        width: 1000px;
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }

    .list-area{
        border: 1px solid white;
        text-align: center;
    }

    .list-area>tbody>tr:hover{
        background-color: gray;
        cursor: pointer;
    }
</style>
</head>
<body>

<%@ include file="../common/menubar.jsp" %>

<div class="outer">
    <h2 align="center">공지사항</h2>
    <br>
    	<!-- 
    		공지사항 작성은 관리자만 가능하게 조건 부여하기 
    		null인지 확인하는 작업을 먼저 작성해야한다. 
    		접근전에 확인 후 벗어나기 위해 (논리추론)
    	-->
	<%if(loginUser != null && loginUser.getUserId().equals("admin")) {%> <!-- 논리연산자는 앞의 t/f를 먼저 판단하기때문에 null이 아닐때의 조건을 먼저 조건넣어줘야함 -->
    <div align="center">
        <a href="<%=contextPath %>/insert.no" class="btn btn-info">공지사항 작성</a> <!-- 관리자만 공지사항 관리할 수 있도록 설정-->
        <br><br>
    </div>
    <%}  %>
    <table class="list-area" align="center">
        <thead>
            <tr>
                <th>글번호</th>
                <th width="400">글제목</th>
                <th width="100">작성자</th>
                <th>조회수</th>
                <th width="100">작성일</th>
            </tr>
        </thead>
        <tbody>
        	<%if(list.isEmpty()){ %><!-- 리스트가 비어있다면 -->
        		<tr>
        			<td>존재하는 공지사항이 없습니다.</td>
        		</tr>
        	<%} else { %> <!-- 목록이 있으면 (반복문으로 전부 출력해주기) -->	
        		<%for(Notice n : list){ %> <!-- 순차적으로 전부 접근할것이기 때문에 향상된 for문 사용 -->
	        		<tr>
		                <td><%=n.getNoticeNo() %></td>
		                <td><%=n.getNoticeTitle() %></td>
		                <td><%=n.getNoticeWriter() %></td>
		                <td><%=n.getCount() %></td>
		                <td><%=n.getCreateDate() %></td>
		            </tr>
        		<%} %>
        	<%} %>
        	
        	<!--
            <tr>
                <td>2</td>
                <td>안녕하세요 등업부탁드립니다</td>
                <td>김탈퇴</td>
                <td>0</td>
                <td>2001-10-05</td>
            </tr>
            <tr>
                <td>5</td>
                <td>공지입니다. 꽁지아님</td>
                <td>admin</td>
                <td>2</td>
                <td>2010-12-10</td>
            </tr>
            <tr>
                <td>7</td>
                <td>사이트 폐쇄합니다. 다 나가주세요.</td>
                <td>관리자</td>
                <td>155</td>
                <td>2023-04-01</td>
            </tr>
            -->
        </tbody>
    </table>
</div>
<script>
    //.list-area클래스 자손 tbody 자손 tr 클릭됐을때
    $(".list-area>tbody>tr").click(function(){
        // 목록에 글을 클릭 했을때 해당 글번호가 있어야
        // 그 글에 대해서 상세조회를 할 수 있으니 글번호 추출하여 서버에 넘기기 (식별자라서 다른 게시물과 구분 가능하기 때문에 글번호를 선택)
        // console.log($(this).children().eq(0).text()); // 확인용
        // this는 tr 인거고 tr의 children은 td들인데 그중에 eq는 인덱스 번호 가리키는 메소드 그래서 0번째인 글번호 지정해 그것의 text를 가져오겠다
        var nno = $(this).children().eq(0).text();
        
        // 요청할 url?키=벨류&키=벨류....
        // 물음표 뒤에 내용들을 쿼리 스트링이라고 한다. - 직접 기술하여 넘기기
        //  /'jsp/detail.no?nno='+nno 
        location.href = '<%=contextPath%>/detail.no?nno='+nno;
        // ?는 매핑주소에 영향을 주지 않음.
    });
</script>
</body>
</html>