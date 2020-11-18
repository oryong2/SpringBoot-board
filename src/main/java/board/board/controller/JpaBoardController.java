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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;
import board.board.service.BoardService;
import board.board.service.JpaBoardService;

//Spring MVC 컨트롤러를 의미
//JPA를 이용한 게시판 컨트롤러
@Controller
public class JpaBoardController {
	
	//비즈니스 로직을 처리하는 서비스빈
	@Autowired
	private JpaBoardService jpaBoardService;

	//아래 경로로 호출하면 Spring Dispatcher는 호출된 주소와 @RequestMapping이 동일한 메서드를 찾아서 실행
	//"글목록" 보기 화면 조회용
	@RequestMapping(value="/jpa/board", method = RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
		
		//ModelAndView : 요청 결과에 해당하는 Model 저장과 요청 결과를 보여줄 화면인 View 정보를 관리하는 클래스
		//생성자로 결과를 보여줄 화면 경로를 지정. Thymeleaf 와 같은 템플릿을 사용하는 경우 .html 확장자 생략가능
		ModelAndView mv = new ModelAndView("/board/jpaBoardList");
		//서비스 호출
		List<BoardEntity> list = this.jpaBoardService.selectBoardList();
		//서비스 호출 결과를 뷰에 list라는 이름으로 저장 
		mv.addObject("list", list);
		
		return mv;
	}
	
	//"글목록"-"글쓰기" 버튼 클릭시 "글쓰기" 화면 return 
	@RequestMapping(value="/jpa/board/write", method = RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		return "/board/jpaBoardWrite";
	}
	
	//"글쓰기"-"저장" 버튼 클릭시 게시글 저장 후 "글목록"화면 redirect
	@RequestMapping(value="/jpa/board/write", method = RequestMethod.POST)
	public String writetBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		this.jpaBoardService.saveBoard(board, multipartHttpServletRequest);
		return "redirect:/jpa/board";
	}
	
	//게시글 상세
	@RequestMapping(value= "/jpa/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int board_idx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/jpaBoardDetail");
		BoardEntity board = this.jpaBoardService.selectBoardDetail(board_idx);
		mv.addObject("board",board);
		return mv;	
	}
	
	//첨부파일 다운로드
	@RequestMapping(value="/jpa/board/file", method = RequestMethod.GET)
	public void downloadBoardFile(@RequestParam int boardIdx
								, @RequestParam int idx
								, HttpServletResponse response) throws Exception{
		
		BoardFileEntity boardFile = this.jpaBoardService.selectBoardFileInfo(boardIdx, idx);
		
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		
	}
	
	//게시글 수정
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(BoardEntity board) throws Exception {
		this.jpaBoardService.saveBoard(board, null);
		return "redirect:/jpa/board";
	}
	
	//게시글 삭제
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		jpaBoardService.deleteBoard(boardIdx);
		return "redirect:/jpa/board";
	}
	

}
