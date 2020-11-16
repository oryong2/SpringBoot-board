package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.service.BoardService;

//Spring MVC 컨트롤러를 의미
@Controller
public class BoardController {
	
	//비즈니스 로직을 처리하는 서비스빈
	@Autowired
	private BoardService boardService;

	//아래 경로로 호출하면 Spring Dispatcher는 호출된 주소와 @RequestMapping이 동일한 메서드를 찾아서 실행
	//"글목록" 보기 화면 조회용
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		
		//ModelAndView : 요청 결과에 해당하는 Model 저장과 요청 결과를 보여줄 화면인 View 정보를 관리하는 클래스
		//생성자로 결과를 보여줄 화면 경로를 지정. Thymeleaf 와 같은 템플릿을 사용하는 경우 .html 확장자 생략가능
		ModelAndView mv = new ModelAndView("/board/boardList");
		//서비스 호출
		List<BoardDto> list = this.boardService.selectBoardList();
		//서비스 호출 결과를 뷰에 list라는 이름으로 저장 
		mv.addObject("list", list);
		
		return mv;
	}
	
	//"글목록"-"글쓰기" 버튼 클릭시 "글쓰기" 화면 return 
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/board/boardWrite";
	}
	
	//"글쓰기"-"저장" 버튼 클릭시 게시글 저장 후 "글목록"화면 redirect
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		this.boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board/openBoardList.do";
	}
	
	//게시글 상세
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int board_idx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		BoardDto board = this.boardService.selectBoardDetail(board_idx);
		mv.addObject("board",board);
		return mv;	
	}
	
	//첨부파일 다운로드
	@RequestMapping("/board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam int idx
								, @RequestParam int boardIdx
								, HttpServletResponse response) throws Exception{
		BoardFileDto boardFile = this.boardService.selectBoardFileInfo(idx, boardIdx);
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
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception {
		this.boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	//게시글 삭제
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(@RequestParam int boardIdx) throws Exception {
		this.boardService.deleteBoard(boardIdx);
		return "redirect:/board/openBoardList.do"; 
	}
	

}
