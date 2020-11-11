package board.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.board.dto.BoardDto;
import board.board.mapper.BoardMapper;

//Spring MVC에서 비즈니스로직 처리하는 서비스 클래스를 나타내는 어노테이션
@Service
public class BoardServiceImpl implements BoardService {

	//DB에 접근하는 DAO 빈 선언
	@Autowired
	private BoardMapper boardMapper;
	
	//요청처리를 위한 비즈니스 로직 구현
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return this.boardMapper.selectBoardList();
	}

}
