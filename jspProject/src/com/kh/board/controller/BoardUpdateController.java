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
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.do")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String boardNo = request.getParameter("bn");
		Board b = null;
		Attachment at = null;
		b = new BoardService().SelectBoardDetail(boardNo);
		at = new BoardService().BoardAttCom(boardNo);
		ArrayList<Category> category = new BoardService().categoryList();
		request.setAttribute("categoryList", category);
		request.setAttribute("at", at);
		request.setAttribute("board", b);
		request.getRequestDispatcher("views/board/boardUpdateForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		if(ServletFileUpload.isMultipartContent(request)) {
			int maxSize = 10 * 1024 * 1024;
			String SavaPath = request.getSession().getServletContext().getRealPath("/resources/board_files/");
			// 여기서 이미 첨부된 파일은 모두 올라간다.
			MultipartRequest multi = new MultipartRequest(request, SavaPath, maxSize, "UTF-8", new MyFileRenamePolicy());
			String title = multi.getParameter("title");
			String content = multi.getParameter("content");
			int boardNo = Integer.parseInt(multi.getParameter("bn"));
			String categoryNo = multi.getParameter("category");
			Board b = new Board();
			b.setCategoryNo(categoryNo);
			b.setBoardTitle(title);
			b.setboardContent(content);
			b.setBoardNo(boardNo);
			Attachment at = null;
			// 새롭게 전달된 첨부파일이 있다면 처리하기
			if(multi.getOriginalFileName("upfile") != null) {
				at = new Attachment();
				at.setOriginName(multi.getOriginalFileName("upfile")); // 원본명
				at.setChangeName(multi.getFilesystemName("upfile")); // 실제 서버에 저장된 파일명 
				at.setFilePath("resources/board_files"); // 저장 경로 (저장된 경로를 보여줄꺼다.)
				// 기존에 첨부파일이 있으면서 수정하는 경우
				int fileNo = Integer.parseInt(multi.getParameter("fileNo"));
				if(multi.getParameter("fileNo") != null) { // ------------------------------------------------------------------> 중요
					at.setFileNo(fileNo);
					// 1.기존 첨부파일 삭제하기.
					new File(SavaPath + "/" + multi.getParameter("OriginFileName")).delete();
				}else {
					// 현재 게시글 번호를 참조하게 insert하기
					at.setRefBno(boardNo);
					int result = new BoardService().updateBoard(b, at);
					// 기존 첨부파일이 없었고 새로운 첨부파일 없는 경우 => boardUpdate
					// 기존 첨부파일이 없었고 새로운 첨부파일 있는 경우 => boardUpdate / AttachmentInsert
					// 기존 첨부파일이 있었고 새로운 첨부파일 있는 경우 => boardUpdate / AttachmentUpdate
					
					if(result > 0) {
						request.getSession().setAttribute("alertMsg", "게시글 수정 성공");
						response.sendRedirect(request.getContextPath() + "detail.do?bn="+boardNo);
					}else {
						request.setAttribute("errorMsg", "게시글 수정 실패");
						request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
					}
					
					
				}
				
				
		}
		
//		 첨부파일이 있는 글이고 첨부파일을 수정하는 경우
//		 첨부파일이 없는 글이고 첨부파일을 추가하는 경우
//			
//			String Orichangname = new BoardService().SelectAttachment(at, boardNo);
//			int check = 1;
//			String Orichangname = ""; 
//			if (check > 0) {
//				// 원래 있었다면
//				Attachment at2 = new BoardService().SelectAttachment(at, boardNo);
//				if(check > 0) {
//					request.getSession().setAttribute("alertMsg", "첨부파일 첨부 수정성공");
//					response.sendRedirect(request.getContextPath() + "/detail.do?bn=" + boardNo);
//					File f = new File(SavaPath + Orichangname); // 첨부파일이 있는 상태로 수정시 원래 파일은 삭제한다.
//					f.delete();
//				}else {
//					request.setAttribute("errorMsg", "첨부파일 첨부 수정 실패");
//					request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
//					File f = new File(SavaPath + at.getChangeName());
//					f.delete();
//				}
//			}else {				
//				// 원래 없었다면
//				int num = new BoardService().updateAttachment(at, boardNo);
//				if(num > 0) {
//					request.getSession().setAttribute("alertMsg", "첨부파일 첨부 수정성공");
//					response.sendRedirect(request.getContextPath() + "/detail.do?bn=" + boardNo);					
//				}else {
//					request.setAttribute("errorMsg", "첨부파일 첨부 수정 실패");
//					request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
//					File f = new File(SavaPath + at.getChangeName());
//					f.delete();
//				}
//			}
//		}else {
//				// 첨부파일이 없는 글이고 첨부파일을 추가하지 않는 경우
//				int num = new BoardService().updateBoard(b);
//				if(num > 0) {
//					request.getSession().setAttribute("alertMsg", "글만 수정성공");
//					response.sendRedirect(request.getContextPath() + "/detail.do?bn=" + boardNo);					
//				}else {
//					request.setAttribute("errorMsg", "글만 수정 실패");
//					request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
//				}
		}
	}

}
