package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.do")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 카테고리 목록 조회해오기
		
		ArrayList<Category> category = new BoardService().categoryList();
		request.setAttribute("categoryList", category);
		request.getRequestDispatcher("views/board/boardEnrollForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		/*
		 * form에서 mutipart/form-data 형식으로 전송했기 때문에 기존 request에 parameter영역에서 꺼낼 수 없다.
		 *  - 이를 위해 특정 라이브러리를 사용하여 전달받아야한다.*/
//		String categroy = request.getParameter("category");
//		String title = request.getParameter("title");
//		String contetnt = request.getParameter("content");
//		String file = request.getParameter("upfile");
//		int userNo = Integer.parseInt(request.getParameter("userNo"));
		
		// cos.jar 라이브러리 추가 후 작업하기
		// form 전송 방식이 일반 방식이 아닌 multipart/form-data 방식이라면
		// request를 multipart 객체로 이관시켜서 다뤄야한다.
		
		// enctype이 multipart로 작동되어 넘어왔을 경우에 수정이 되도록
		if(ServletFileUpload.isMultipartContent(request)) { // isMultipartContent는 boolean값을 반환하며 mutipart방식인지 확인한다.
			// 이때가 multipart 방식으로 받았을 경우
			System.out.println("들어왔다");
			// 1. 전송되는 파일을 처리할 작업내용 (용량 제한, 전달된 파일을 저장할 경로 설정)
			
			// 1-1. 용량 제한 하기 (10Mbyte)
			//   --> byte - kbyte - mbyte - gbyte - tbyte
			int maxSize = 10 * 1024 * 1024;
			
			// 1-2. 전송된 파일 저장 경로 설정
			/*
			 * 세션 객체에서 제공하는 getRealPath 메서도를 사용
			 * WebContent에 board_files 폴더 경로까지는 주어야한다. 
			 * 해당 폴더에 저장될것이기 때문이다.*/
			// 경로 마지막에 / 붙여주기 ( 그 폴더 안에 넣을꺼기 때문이다.)
			String SavePath = request.getSession().getServletContext().getRealPath("/resources/board_files/"); // 
					//request.getSession().getServletContext() >> 실질적인 서버의 경로
					//request.getSession().getServletContext().getPath("/") >> WebContent의 경로를 나타낸다. c:\\sever-workspace\jspProject\WebContent\
			/*
			 * 2. 전달된 파일명 수정 및 서버에 업로드 작업
			 * 		HttpServletRequest request -> MultipartRequest mutiRequest로 변환하기
			 * 
			 * 매개변수 생성자로 생성
			 * MutipartRequest mutiRequest = new MutipartRequest(request객체, 저장할 폴더 경로, 용량제한, encoding, 파일명 수정객체)
			 * 
			 * 위 구문 한줄이 실행되는 순간 지정한 경로에 파일이 업로드된다.
			 * 사용자가 올린 파일명은 그래도 해당 폴더에 업로드하지 않는게 일반이다. - 같은 파일명이 있을경우 덮어씌워질 경우가 도있고 한글 / 특수문자가 포함된 파일명의 경우 업로드되는 서버에 따라 오류가 발생할 수 있다.
			 * 이러한 이유로 파일명을 변경해주는 객체를 제공한다.
			 * DefaultFileRenamePolicy 객체이다.
			 * 내부적으로 rename()이라는 메서드가 실행되며 파일명 수정을 해준다.
			 * 기본적으로 제공된 객체는 파일명 수정을 파일명 뒤에 숫자를 카운팅하는 형식으로만 지행되낟.
			 * ex) hello1.jpg, hello2.jpg ...
			 * 
			 * 해당 rename 작업을 따로 정의하여 사용해 볼것 (내가 원하는 방식대로)
			 * */
			
			MultipartRequest multiRequest = new MultipartRequest(request, SavePath, maxSize, "UTF-8", new MyFileRenamePolicy()); // 오버라이딩으로 새로운 형식
//			MultipartRequest multiRequest = new MultipartRequest(request, SavePath, maxSize, "UTF-8", new DefaultRenamePolicy()); >> cos에서 지정된 간단한 방볍
			
			//3. DB에 저장할 정보들 추출하기.
			// - 카테고리 번호, 제목, 내용, 사용자 번호
			String categoryNo = multiRequest.getParameter("category");
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo"); // userNo를 뽑은거지만 board의 변수와 이름을 맞춰서 헷갈리지 않도록 한거다.
			
			Board b = new Board();
			b.setBoardTitle(title);
			b.setboardContent(content);
			b.setBoardWriter(boardWriter);
			b.setCategoryNo(categoryNo);
			
			Attachment at = null; // 첨부파일의 유무에 따라 객체를 생성할지 말지 결정한다.
			
			// multiRequest.getOriginalFileName("key") : 첨부파일이 있으면 원본명을 반환하고 없다면 null을 반환한다.
			if(multiRequest.getOriginalFileName("upfile") != null) {
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("upfile")); // 원본명
				at.setChangeName(multiRequest.getFilesystemName("upfile")); // 실제 서버에 저장된 파일명 
				// 원본명과 실제 서버에 저장된 파일명은 다르다.
				
				at.setFilePath("resources/board_files"); // 저장 경로 (저장된 경로를 보여줄꺼다.)
			}
			
			int num = new BoardService().insertBoard(b,at);
			if(num > 0 ) {
				request.getSession().setAttribute("alertMsg", "게시글 작성 성공");
				response.sendRedirect(request.getContextPath() + "/list.do?currentPage=1");
			}else {
				if(at != null) {
					File f = new File(SavePath + at.getChangeName());
					if(f.delete()) {
						request.setAttribute("errorMsg", "게시글 작성 실패 (파일 삭제 완료)");
					}else {
						request.setAttribute("errorMsg", "게시글 작성 실패 (파일 삭제 실패)");						
					}
				}
				// 실패시에는 업로드되 파일을 지워주는 작업이 필요하다. (왜냐하면 우선적으로 파일이 저장되기 때문이다.)
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
		}else {
			System.out.println("나갔따");
		}
	}

}
