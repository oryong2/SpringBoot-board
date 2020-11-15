package board.board.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

//컨트롤러에 글로벌 하게 설정하도록 하는 어노테이션 (주로 예외처리에 사용하는 경우가 많음)
@ControllerAdvice
public class ExceptionHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//Exception 예외를 처리하기 위한 설정
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception){
		ModelAndView mv = new ModelAndView("/error/error_default");
		mv.addObject("exception", exception);
		log.error("exception", exception);
		
		return mv;
	}
}
