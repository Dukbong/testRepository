package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {

	public Member loginMember(String userId, String userPwd) {
		Connection conn = JDBCTemplate.getConnection();
		// Connection Objet Make
		
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		JDBCTemplate.close(conn);
		return m;
	}

	public int insertMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		
		int num = new MemberDao().insertMember(conn, m);
		
		if(num > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return num;
	}

	public Member updateMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		
		int num = new MemberDao().updateMember(conn, m);
		Member updateM = null;
		
		
		if(num > 0) {
			JDBCTemplate.commit(conn);
			updateM = new MemberDao().selectMember(conn, m);
		}else {
			JDBCTemplate.rollback(conn);			
		}
		JDBCTemplate.close(conn);					
		return updateM;
	}

	public int updatePassword(String updatePassword, String userId) {
		Connection conn = JDBCTemplate.getConnection();
		int num = new MemberDao().updatePassword(conn, updatePassword, userId);
		if(num > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);			
		}
		JDBCTemplate.close(conn);					
		return num;
	}

	public int deleteMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		int num = new MemberDao().deleteMember(conn,m);
		if(num > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);			
		}
		JDBCTemplate.close(conn);					
		return num;
	}
	
}