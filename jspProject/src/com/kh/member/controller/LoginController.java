package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * HttpServletRequest객체와 HttpSerletResponse객체
		 * - request : 서버로 요청할 때의 정보들이 담겨있다 (요청시 전달값, 요청 전송 방식 등등)
		 * - response : 요청에 대해 응답에 필요한 객체
		 * 
		 * GET / POST
		 * -GET : 사용자가 입력한 값이 url에 노출되고 데이터의 길이 제한이 있으며 즐겨찾기를 할수 있다.
		 * -POST : url에 노출되지 않고 데이터의 길이 제한이 없으며 즐겨찾기를 할 수 없다.
		 * */
		
		// 1) POST 요청일 경우 전달값에 한글이 포함되어 있다면 encodeing 설정을 해야한다.
		request.setCharacterEncoding("UTF-8");
		// 2) 요청시 전달한 데이터를 추출한다.
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		// 조회해온 Member 객체 (LoginUser)
		Member loginUser = new MemberService().loginMember(userId, userPwd);
		
		/*
		 * 응답페이지에 전달할 값이 있다면 값을 어딘가엔 담아서 전달해야한다.
		 * (이때 담아줄 수 있는 내장객체 4가지)
		 * 
		 * -servlet scope
		 * 1) application : application에 담은 데이터는 웹 애플리케이션 전역에서 꺼내쓸 수 있다.
		 * 2) session : session 에 담은 데이터는 모든 jsp로 servlet에서 꺼내쓸 수 있다.
		 * 				한 번 담은 데이터는 직접 지우기 전까지, 서버가 멈추기전까지, 브라우저가 종료되기 전까지는 사용가능하다.
		 * 3) request : request에 담은 데이터 해당 request를 포워딩한 응답 jsp에서만 꺼내쓸 수 있다.
		 * 4) page : 해당 jsp 페이지에서만 꺼내쓸 수 있음.
		 * 
		 * 
		 * 공통적으로 데이터를 담고자 한다면
		 * - setAttribute("키", "밸류")
		 * 데이터를 꺼내고자 담고자 한다면
		 * - getAttribute("키")
		 * 데이터를 지우고자 한다면
		 * - removeAttribute("키")
		 * */
		
		if(loginUser == null) { // 로그인 실패 (에러 페이지로 응답해보기)
//			System.out.println(request.getRequestDispatcher("index3.jsp"));
			request.setAttribute("errorMsg", "로그인에 실패하였습니다.");
			
//			RequestDispatcher dp = request.getRequestDispatcher("views/common/errorPage.jsp");
			RequestDispatcher dp = request.getRequestDispatcher("index3.jsp"); // webContent가 최상위 경로입니다.
			dp.forward(request, response);
		}else { // 로그인 성공 - 인덱스 페이지로 돌아가기
			// 로그인 한 회원의 정보를 가지고 다닐것이기 때문에 Session에 담아준다.
			HttpSession sess = request.getSession();
			sess.setAttribute("loginUser", loginUser); // 객체 자체를 담는다.
			sess.setAttribute("alertMsg", "성공적으로 로그인 되었습니다.");
			
			// 응답 방식 2가지
			// 1) forward - 요청을 유지한채로 위임하는 방법 (url에 기존 주소가 남아있다.)
			/*
			 * RequestDispatcher view = request.getRequestDispatcher("경로")
			 * view.forward(request, response)*/
			// 2) redirect - 새로운 페이지를 보여주는 방식 (url에 기존 주소가 없음)
//			response.sendRedirect("/jsp"); // 경로에 "/jsp" 라고 하면 루트 인덱스 페이지로 간다.  >> localhost888/jsp 로 열겠다.
			response.sendRedirect(request.getHeader("Referer")); // 둘다 같은 내용이며 아래꺼를 쓰는게 좋다.
		}
	}

}