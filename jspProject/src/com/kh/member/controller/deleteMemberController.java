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
 * Servlet implementation class deleteMemberController
 */
@WebServlet("/deleteMember.me")
public class deleteMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteMemberController() {
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
		Member m = (Member)request.getSession().getAttribute("loginUser");
		int num = new MemberService().deleteMember(m);
		
		if(num > 0) {
			request.getSession().setAttribute("alertMsg", "회원탈퇴가 되었습니다");
			request.getSession().removeAttribute("loginUser");
			response.sendRedirect(request.getContextPath());
		}else {
			request.setAttribute("errorMsg", "회원탈퇴에 실패하였습니다");
			request.getRequestDispatcher(request.getContextPath()).forward(request, response);
			
			
		}
	}

}
