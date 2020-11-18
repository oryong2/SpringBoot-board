package board.configuration;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//application.properties 를 사용할 수 있도록 위치 지정
//@PropertySource 추가하여 다른 설정 파일도 사용 가능
@PropertySource("classpath:/application.properties")
public class JpaHibernateConfiguration {
	
	@Bean
	//JPA 사용을 위한 설정
	@ConfigurationProperties(prefix = "spring.jpa")
	public Properties hibernateConfig() throws Exception {
		return new Properties();
	}

}
