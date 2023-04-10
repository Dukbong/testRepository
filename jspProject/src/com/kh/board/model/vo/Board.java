package com.kh.board.model.vo;
import java.sql.Date;
public class Board {
//	BOARD_NO	NUMBER
//	BOARD_TYPE	NUMBER
//	CATEGORY_NO	NUMBER
//	BOARD_TITLE	VARCHAR2(100 BYTE)
//	BOARD_CONTENT	VARCHAR2(4000 BYTE)
//	BOARD_WRITER	NUMBER
//	COUNT	NUMBER
//	CREATE_DATE	DATE
//	STATUS	VARCHAR2(1 BYTE)
	
	private int boardNo;
	private int boradType;
	private String categoryNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private int count;
	private Date createDate;
	private String status;
	
	
	public Board() {
		super();
	}
	public Board(int boardNo, int boradType, String categoryNo, String boardTitle, String boardContent, String boardWriter,
			int count, Date createDate, String status) {
		super();
		this.boardNo = boardNo;
		this.boradType = boradType;
		this.categoryNo = categoryNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.count = count;
		this.createDate = createDate;
		this.status = status;
	}
	
	public Board(int boardNo, String categoryNo, String boardTitle, String boardWriter, int count, Date createDate) {
		super();
		this.boardNo = boardNo;
		this.categoryNo = categoryNo;
		this.boardTitle = boardTitle;
		this.boardWriter = boardWriter;
		this.count = count;
		this.createDate = createDate;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getBoradType() {
		return boradType;
	}
	public void setBoradType(int boradType) {
		this.boradType = boradType;
	}
	public String getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getboardContent() {
		return boardContent;
	}
	public void setboardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardWriter() {
		return boardWriter;
	}
	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", boradType=" + boradType + ", categoryNo=" + categoryNo + ", boardTitle="
				+ boardTitle + ", boardContent=" + boardContent + ", boardWriter=" + boardWriter + ", count=" + count
				+ ", createDate=" + createDate + ", status=" + status + "]";
	}
	
	
}