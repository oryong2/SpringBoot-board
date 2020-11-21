package board.board.service;

import java.util.List;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.common.FileUtils;
import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.mapper.BoardMapper;


//Spring MVC에서 비즈니스로직 처리하는 서비스 클래스를 나타내는 어노테이션
@Service
//트랜잭션 처리(경계설정)을 위한 어노테이션. TransacitonInterceptor를 이용하는 방식의 경우 comment 할 것 
@Transactional
public class BoardServiceImpl implements BoardService {

	//DB에 접근하는 DAO 빈 선언
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	//요청처리를 위한 비즈니스 로직 구현
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return this.boardMapper.selectBoardList();
	}
	
	//게시글 등록
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		this.boardMapper.insertBoard(board);
		
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		
		if(CollectionUtils.isEmpty(list) == false) {
			this.boardMapper.insertBoardFileList(list);
		}
	}

	//게시글 상세
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		//트랜잭션 테스트 위한 부분으로 테스트를 위해서는 uncomment
		//int i=10/0;
		BoardDto board = this.boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> fileList = this.boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);		
		this.boardMapper.updateHitCount(boardIdx);
		return board;
	}
	
	//첨부파일 다운로드를 첨부파일 정보 조회
	@Override
	public BoardFileDto selectBoardFileInfo(int idx, int boardIdx) throws Exception{
		return this.boardMapper.selectBoardFileInfo(idx, boardIdx);
	}

	//게시글 수정
	@Override
	public void updateBoard(BoardDto board) throws Exception {
		this.boardMapper.updateBoard(board);		
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		this.boardMapper.deleteBoard(boardIdx);		
	}

}
