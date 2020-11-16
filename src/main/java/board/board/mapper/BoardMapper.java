package board.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;

//MyBatis의 매퍼 인터페이스 임을 선언
@Mapper
//SQL xml의 <mapper namespace="패키지명.아래 인터페이스 이름">과 동일할 것
public interface BoardMapper {
	
	//매퍼와 XML의 쿼리를 매칭하여 사용하기 위해서는 
	//아래 메소드 이름과 SQL XML의 <select id="아래 메소드 이름" 과 같이 동일해야함.
	List<BoardDto> selectBoardList() throws Exception;
	
	//글쓰기 처리
	void insertBoard(BoardDto board) throws Exception;
	
	//첨부파일 저장
	void insertBoardFileList(List<BoardFileDto> list) throws Exception;
	
	//첨부파일들 정보조회 (게시글 상세에서 첨부파일 리스트를 보여주기 위함)
	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
	
	//첨부파일 다운로드를 위한 정보조회
	//@Param 을 사용하면 해당 파라미터들이 Map에 저장되어 DTO를 만들지 않아도 여러개의 파라미터를 전달할 수 있다.
	BoardFileDto selectBoardFileInfo(@Param("idx") int idx, @Param("boardIdx") int boardIdx) throws Exception;
		
	//조회수 증가
	void updateHitCount(int boardIdx) throws Exception;
	
	//게시글 상세조회
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	
	//게시글 수정
	void updateBoard(BoardDto board) throws Exception;
	
	//게시글 삭제
	void deleteBoard(int boardIdx) throws Exception;
}
