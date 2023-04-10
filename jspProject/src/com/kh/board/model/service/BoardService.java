package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.JDBCTemplate;
import com.kh.common.PageInfo;

public class BoardService {
	
	public ArrayList<Board> selectList(PageInfo pi) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Board> list = new BoardDao().selectList(conn,pi);
		
		JDBCTemplate.close(conn);
		return list;
	}

	public int selectListCount() {
		Connection conn = JDBCTemplate.getConnection();
		int listCount= new BoardDao().selectListCount(conn);
		JDBCTemplate.close(conn);
		return listCount;
	}

	public int insertBoard(Board b, Attachment at) {
		Connection conn = JDBCTemplate.getConnection();
		int insertAttachment = 1;
		int insertBoard = new BoardDao().insertBoard(conn, b);			
		if(at != null) {
			insertAttachment = new BoardDao().insertAttachment(conn, at);
		}
		if(insertAttachment > 0 && insertBoard > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return insertAttachment * insertBoard;
	}
	
	public ArrayList<Category> categoryList(){
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Category> list = new BoardDao().categoryList(conn);
		
		JDBCTemplate.close(conn);
		return list;
	}

	public Board SelectBoardDetail(String boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		Board b = new BoardDao().SelectBoardDetail(conn, boardNo);
//		System.out.println("service = "  + list);
		JDBCTemplate.close(conn);
		return b;
	}

	public Attachment BoardAttCom(String boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		Attachment at = new BoardDao().BoardAttcom(conn, boardNo);
//		System.out.println("service = "  + list);
		JDBCTemplate.close(conn);
		return at;
	}

	public int increaseCount(String boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		int count = new BoardDao().increaseCount(conn, boardNo);			
		if(count > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return count;
	}
	
	

	public int updateBoard(Board b, Attachment at) {
		Connection conn = JDBCTemplate.getConnection();
		int count = new BoardDao().updateBoard(conn, b);	
		int result = 1;
		if(at != null) {
			if(at.getFileNo() != 0) { // 기존에 있었음
				result = new BoardDao().updateAttachment(conn,at);
			}else { // 기존에 없었음
				result = new BoardDao().newInsertAttachment(conn,at);
			}
		}
		if(count > 0 && result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return count * result;
	}

	public int deleteBoard(int boradNo) {
		Connection conn = JDBCTemplate.getConnection();
		int count = new BoardDao().deleteBoard(conn, boradNo);	
		if(count > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return count;
	}
}
