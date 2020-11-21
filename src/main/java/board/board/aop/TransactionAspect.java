package board.board.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

//아래는 TransacitonInterceptor를 이용한 transaction 을 테스트 해보기 위한 클래스로 @Configuration 을 uncomment 하면 동작
//@Transactional 사용하는 방법과 상충하므로 @Transactional 사용시는 @Configuration comment할 것
//@Configuration
public class TransactionAspect {
	
	//트랜잭션 설정할 때 사용되는 설정값을 상수로 선언
	private static final String AOP_TRAN_METHOD_NAME="*";
	private static final String AOP_TRAN_EXPRESSION="execution(* board..service.*Impl.*(..))";
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public TransactionInterceptor trasactionAdvice() {
		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		RuleBasedTransactionAttribute transactionAttribute= new RuleBasedTransactionAttribute();
		
		//트랜잭션의 이름을 지정
		transactionAttribute.setName(AOP_TRAN_METHOD_NAME);
		//롤백 Rule 을 지정, 예제를 위해 모든 Exception 시 롤백
		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		source.setTransactionAttribute(transactionAttribute);
		
		return new TransactionInterceptor(transactionManager, source);
	}
	
	@Bean
	public Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		//포인트컷을 지정
		pointcut.setExpression(AOP_TRAN_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, trasactionAdvice());
	}
}
