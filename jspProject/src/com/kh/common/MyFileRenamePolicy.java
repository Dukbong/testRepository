package com.kh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {
	
	// 파일명 변경 메서드 오버라이딩
	// 기존의 파일명을 받아서 수정 작업후 해당 파일명 반환
	@Override
	public File rename(File originFile) {
		// TODO Auto-generated method stub
		// 원본 파일명
		String origin = originFile.getName();
		
		// 수정 파일명 :: 파일업로드 시간(년월일시분초)+5랜덤값.확장자
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime =sdf.format(now);
		
		int num = (int)(Math.random() * 90000)+10000;
		
		String lastName = origin.substring(origin.lastIndexOf("."));
		
		String changeName = currentTime+num+lastName;
		
		return new File(originFile.getParent(), changeName);
		// getParent 해당 파일이 있는 곳의 상위 > originFile은 파일이기 때문에 폴더를 찍는다는거다.
	}
	
}

