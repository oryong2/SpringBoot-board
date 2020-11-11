package board.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.board.dto.BoardDto;

//MyBatis의 매퍼 인터페이스 임을 선언
@Mapper
//SQL xml의 <mapper namespace="패키지명.아래 인터페이스 이름">과 동일할 것
public interface BoardMapper {
	
	//매퍼와 XML의 쿼리를 매칭하여 사용하기 위해서는 
	//아래 메소드 이름과 SQL XML의 <select id="아래 메소드 이름" 과 같이 동일해야함.
	List<BoardDto> selectBoardList() throws Exception;

}
