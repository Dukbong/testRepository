package com.kh.board.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.JDBCTemplate;
import com.kh.common.PageInfo;

public class BoardDao {
	private Properties prop = new Properties();

	public BoardDao() {
		String filePath = BoardDao.class.getResource("/sql/board/board-mapper.xml").getPath();
		try {
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Board> selectList(Connection conn, PageInfo pi) {
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectList");
		ArrayList<Board> list = new ArrayList<>();
		Board b = null;
		try {
			int start = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int end = (start + pi.getBoardLimit()) - 1;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				b = new Board(rset.getInt("BOARD_NO"), rset.getString("CATEGORY_NAME"), rset.getString("BOARD_TITLE"),
						rset.getString("USER_ID"), rset.getInt("COUNT"), rset.getDate("CREATE_DATE"));
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int selectListCount(Connection conn) {
		ResultSet rset = null;
		Statement stmt = null;
		String sql = prop.getProperty("selectListCount");
		int listCount = 0;

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}

		return listCount;
	}

	public int insertBoard(Connection conn, Board b) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoard");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getCategoryNo());
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getboardContent());
			pstmt.setString(4, b.getBoardWriter());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int insertAttachment(Connection conn, Attachment at) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachment");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<Category> categoryList(Connection conn) {
		ArrayList<Category> list = new ArrayList<>();
		ResultSet rset = null;
		String sql = prop.getProperty("categoryList");
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				list.add(new Category(rset.getInt("CATEGORY_NO"), rset.getString("CATEGORY_NAME")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		return list;
	}

	public Board SelectBoardDetail(Connection conn, String boardNo) {
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("SelectBoardDetail");
		Board b = null;
//		ArrayList<Object> list = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			rset = pstmt.executeQuery();
			if (rset.next()) {
//				 list = new ArrayList<>();
				b = new Board();
				b.setCategoryNo(rset.getString("CATEGORY_NAME"));
				b.setBoardTitle(rset.getString("BOARD_TITLE"));
				b.setBoardWriter(rset.getString("USER_ID"));
				b.setCreateDate(rset.getDate("CREATE_DATE"));
				b.setboardContent(rset.getString("BOARD_CONTENT"));
//				 list.add(b);
//				 list.add(rset.getString("CHANGE_NAME") + rset.getString("FILE_PATH"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
//		System.out.println("dao = " + list);
		return b;
	}

	public Attachment BoardAttcom(Connection conn, String boardNo) {
		Attachment at = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("BoardAttcom");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				at = new Attachment();
				at.setChangeName(rset.getString(2));
				at.setFilePath(rset.getString(1));
				at.setOriginName(rset.getString("ORIGIN_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return at;
	}

	public int increaseCount(Connection conn, String boardNo) {
		int count = 0;
		String sql = prop.getProperty("increaseCount");
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return count;

	}

	public int updateBoard(Connection conn, Board b) {
		int count = 0;
		String sql = prop.getProperty("updateBoard");
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getBoardTitle());
			pstmt.setString(2, b.getboardContent());
			pstmt.setString(3, b.getCategoryNo());
			pstmt.setInt(4, b.getBoardNo());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return count;
	}

	public int updateAttachment(Connection conn, Attachment at) {
		int count = 0;
		String sql = prop.getProperty("updateAttachment");
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			pstmt.setInt(4, at.getFileNo());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return count;
	}

	public int newInsertAttachment(Connection conn, Attachment at) {
		int count = 0;
		String sql = prop.getProperty("newInsertAttachment");
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, at.getRefBno());
			pstmt.setString(2, at.getOriginName());
			pstmt.setString(3, at.getChangeName());
			pstmt.setString(4, at.getFilePath());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return count;
	}

	public int deleteBoard(Connection conn, int boradNo) {
		int count = 0;
		String sql = prop.getProperty("deleteBoard");
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boradNo);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return count;
	}

}
