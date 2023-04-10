package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;


public class JDBCTemplate {
	public static Connection getConnection() {
		Properties prop = new Properties();
		Connection conn = null;
		
		// 읽어 들이고자하는 dirver.properties 파일의 경로를 알아내서 대입해야한다.
		// 아래 filePath를 하는 이유는 배포시 어디서나 driver.properties에 접근할 수 있도록 만들기 위해서다.
			String filePath = JDBCTemplate.class.getResource("/sql/driver/driver.properties").getPath();
			// properties가 변경되면 그때그때 새롭게 적용도 되기 때문에 편하다.
//			System.out.println(filePath);
			try {
				prop.load(new FileInputStream(filePath)); // properties 읽어라~
				
				// 1. jdbc dirver 등록
				Class.forName(prop.getProperty("driver"));
				
				// 2. conn객체 생성
				conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
				conn.setAutoCommit(false); // --> 기본값이 true이며 2개 이상의 트랜젝션 처리시 발생할 수 있는 오류를 잡아준다.
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) { 
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return conn;
	}
	
	// 2. 전달 바은 Connection 객체를 가지고 commit & rollback 해주는 메서드 만들기
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3. 전달받은 connection 객체를 반납시켜주는 메서드
	public static void close(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 4. sql문 실행시 생성될 수 있는 객체들 반납메서드 ( Statement, ResultSet 등등)
	public static void close(ResultSet rset) {
		try {
			if(rset != null && rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null && stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
