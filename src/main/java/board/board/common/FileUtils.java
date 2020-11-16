package board.board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardFileDto;

//클래스를 스프링 빈으로 등록
@Component
public class FileUtils {

	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest)
			throws Exception{
		List<BoardFileDto> fileList = null;
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		fileList = new ArrayList<>();
		//파일이 업로드될 폴더를 images/yyyyMMdd로 생성 
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		//JDK 1.8 이상에서 오늘의 날짜 확인을 위해 사용
		ZonedDateTime current = ZonedDateTime.now();
		//Project 경로 아래에 images 는 미리 생성해 놓아야 한다.
		String path="images/"+current.format(format);
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdir();
		}
		//화면에서 사용되는 file 태그 이름들을 가져온다.
		Iterator<String> fileTags = multipartHttpServletRequest.getFileNames();
		String contentType, originalFileExtention, newFileName;
		while(fileTags.hasNext()) {
			//MultipartFile : 업로드된 파일을 나타내는 인터페이스
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(fileTags.next());
			for(MultipartFile multipartFile  : list) {
				if(multipartFile.isEmpty() == false) {
					//파일 확장자는 바꾸어서 변조하여 업로드 가능하므로 실제 파일 타입을 가지고 확장자 결정
					//실제로 개발시에는 nio 패키지를 사용하던지 Apache Tika 라이브러리 사용할 것 
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtention =".jpg";
						}
						else if(contentType.contains("image/png")) {
							originalFileExtention =".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtention =".png";
						}
						else {
							break;
						}
					}
					//서버에 저장될 이름 생성. 중복 방지를 위해 System.nanoTime으로 나노초 이용하여 파일명 지정
					newFileName = Long.toString(System.nanoTime()) + originalFileExtention;
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path+"/"+newFileName);
					fileList.add(boardFile);
					file = new File(path+"/" + newFileName);
					//업로드된 파일을 새로운 이름으로 바꾸어 저장. 파일명 중복될때 ~(1), ~(2)등과 같이 생기는 것을 방지
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}
}
