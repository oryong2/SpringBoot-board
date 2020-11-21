package board.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
//application.properties 를 사용할 수 있도록 위치 지정
//@PropertySource 추가하여 다른 설정 파일도 사용 가능
//applicaation.yml 로 전환되면서 주석처리 @PropertySource("classpath:/application.properties")
public class DS4HikariConfiguration {
	
	@Bean
	//application.properties파일의 spring.datasource.hikari prefix로 되어 있는 설정 사용
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	//위의 Hikari CP 설정 이용하여 DS 생성
	public DataSource dataSource() throws Exception {
		DataSource dataSource = new HikariDataSource(this.hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}

}
