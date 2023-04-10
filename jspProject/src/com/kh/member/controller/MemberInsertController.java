package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberInsertController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 넘겨 받은 데이터 가공처리 후 > service -> dao
		// Member 객체로 생성후 service에 전달 > service에서 Connection 객체 생성 후 > dao에 전달 > xml에
		// sel 구문 작성 후 db에 저장 결과 값 반환 해서 여기까지 끌고 오기
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
//		String chkPwd = request.getParameter("chkPwd");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] interest = request.getParameterValues("interest");
		
		
		
		Member m = new Member(userId, userPwd, userName, phone, email, address,
				interest != null ? String.join(",", interest) : "");
		int num = new MemberService().insertMember(m);
		System.out.println(num);
		if( num > 0) {
			request.getSession().setAttribute("alertMsg", "회원가입을 축하드립니다.");
			response.sendRedirect(request.getContextPath()); // 재요청 
					
		}else {
			request.setAttribute("errorMsg", "회원가입에 실패하셨습니다.");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);	
		}
		// 성공시 index로 돌아가 alertMsg = 회원강비 축하드림
		// 결과 값이 양수 면 forward에 키값 밸류값 넣어서 index로 돌아가서 가진거 풀어서 보여주기
		// 실패시 errorPage로 위임 -msg = 회원강비에 실패했음
		// 결과 값이 0이하면 forward로 해서 키값 밸류값 넣어서 error 페이지로 가서 가진거 풀어주기
	}

}
