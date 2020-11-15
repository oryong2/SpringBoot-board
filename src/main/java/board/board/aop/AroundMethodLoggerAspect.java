package board.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//메소드 실행 전후 메서드 경로 및 이름 logging 하는 Aspect
@Component
@Aspect
public class AroundMethodLoggerAspect {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//대상 메서드의 호출전후, 예외발생 등 모든 시점에 적용할 수 있는 어드바이스
	@Around("execution(* board..controller.*Controller.*(..)) or "
			+"execution(* board..service.*Impl.*(..)) or "
			+"execution(* board..mapper.*Mapper.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
		
		String type="";
		String name=joinPoint.getSignature().getDeclaringTypeName();
		
		if(name.indexOf("Controller") > -1) {
			type="Controller \t: ";
		}
		else if(name.indexOf("Service") > -1) {
			type="ServiceImpl \t: ";
		}
		else if(name.indexOf("Mapper") > -1) {
			type="Mapper \t: ";
		}
		
		log.debug(type+name+"."+joinPoint.getSignature().getName()+"()");
		return joinPoint.proceed();
		
	}
	
}
