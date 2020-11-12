package board.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.service.BoardService;

//Spring MVC 컨트롤러를 의미
@Controller
public class BoardController {
	
	//비즈니스 로직을 처리하는 서비스빈
	@Autowired
	private BoardService boardService;
	
	//아래 경로로 호출하면 Spring Dispatcher는 호출된 주수와 @RequestMapping이 동일한 메서드를 찾아서 실행
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

}
