package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.service.BoardService;

//REST API를 위한 Controller. 
//@RestController = @Controller + @ResponseBody로 요청의 결과 값을 JSON 형태로 Web response body에 만들어 줌. 
@RestController
public class RestBoardApiController {
	
	//비즈니스 로직을 처리하는 서비스빈
	@Autowired
	private BoardService boardService;

	//아래 경로로 호출하면 Spring Dispatcher는 호출된 주소와 @RequestMapping이 동일한 메서드를 찾아서 실행
	//"글목록" 조회용 REST API
	@RequestMapping(value="/api/board", method = RequestMethod.GET)
	public List<BoardDto> openBoardList() throws Exception{
		//서비스 호출
		List<BoardDto> list = this.boardService.selectBoardList();
		return list;
	}
	
	//게시글 저장 REST API
	@RequestMapping(value="/api/board/write", method = RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto board) throws Exception{
		this.boardService.insertBoard(board, null);
	}
	
	//게시글 상세 REST API
	@RequestMapping(value= "/api/board/{boardIdx}", method=RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") int board_idx) throws Exception{
		return this.boardService.selectBoardDetail(board_idx);
	}
	
	//게시글 수정
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(@RequestBody BoardDto board) throws Exception {
		this.boardService.updateBoard(board);
		return "redirect:/api/board";
	}
	
	//게시글 삭제
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/api/board";
	}
	

}
