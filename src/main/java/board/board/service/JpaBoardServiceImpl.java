package board.board.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.common.FileUtils;
import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;
import board.board.mapper.BoardMapper;
import board.board.repository.JpaBoardRepository;


//Spring MVC에서 비즈니스로직 처리하는 서비스 클래스를 나타내는 어노테이션
@Service
//트랜잭션 처리(경계설정)을 위한 어노테이션. TransacitonInterceptor를 이용하는 방식의 경우 comment 할 것 
@Transactional
public class JpaBoardServiceImpl implements JpaBoardService {

	//DB에 접근하는 JPA Repository 빈 선언
	@Autowired
	private JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	private FileUtils fileUtils;
	
	//요청처리를 위한 비즈니스 로직 구현
	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		
		return this.jpaBoardRepository.findAllByOrderByBoardIdxDesc();
	}
	
	//게시글 등록/수정
	@Override
	public void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{

		board.setCreatorId("admin");
		
		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
			List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
			if (CollectionUtils.isEmpty(list) == false) {
				board.setFileList(list);
			}
		}
		//save 메서드는 JPA에서 기본 제공
		this.jpaBoardRepository.save(board);
	}

	//게시글 상세
	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception {

		//Optional 클래스는 JDK 1.8에서 NullPointerException을 방지하기 위해추가된 클래스
		//어떤 객체의 값으로 Null이 아닌 값을 가지고 있으며 절대로 null이 아님.
		Optional<BoardEntity> optional = this.jpaBoardRepository.findById(boardIdx);
		
		//Optional 객체의 경우 isPresent로 객체값 존재유무 판단
		if(optional.isPresent()) {
			BoardEntity board = optional.get();
			board.setHitCnt(board.getHitCnt()+1);
			this.jpaBoardRepository.save(board);

			return board;
		}
		else {
			throw new NullPointerException();
		}
	}
	
	//첨부파일 다운로드를 첨부파일 정보 조회
	@Override
	public BoardFileEntity selectBoardFileInfo(int boardIdx, int idx) throws Exception{
		return this.jpaBoardRepository.findBoardFile(boardIdx, idx);
	}


	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		//deleteById 메서드는 JPA에서 기본 제공
		this.jpaBoardRepository.deleteById(boardIdx);		
	}

}
