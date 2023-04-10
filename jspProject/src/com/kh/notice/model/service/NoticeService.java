package com.kh.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

public class NoticeService {

	// 공지사항 리스트 조회 메소드
	public ArrayList<Notice> selectList() {
		Connection conn = JDBCTemplate.getConnection();

		// 조회구문이기때문에 트랜잭션 처리 할 필요 없음
		ArrayList<Notice> list = new NoticeDao().selectList(conn);

		JDBCTemplate.close(conn);

		return list;
	}

	// 공지사항 등록 메소드
	public int insertNotice(Notice n) {

		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().insertNotice(conn, n);

		// DML구문 - 트랜잭션
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return result;

	}

	public Notice selectNotice(int nno) {
		Connection conn = JDBCTemplate.getConnection();

		Notice notice = new NoticeDao().selectNotice(conn, nno);

		JDBCTemplate.close(conn);
		return notice;
	}

	public int addCount(int nno) {
		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().addCount(conn, nno);

		// DML구문 - 트랜잭션
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return result;
	}

	public int UpdateNotice(String title, String content, int no) {
		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().updateNotice(conn, title, content, no);

		// DML구문 - 트랜잭션
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return result;
	}

	public int deleteNotice(int no) {
		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().deleteNotice(conn, no);

		// DML구문 - 트랜잭션
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return result;
		
	}

}
