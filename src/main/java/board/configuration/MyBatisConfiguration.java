package board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//application.properties 를 사용할 수 있도록 위치 지정
//@PropertySource 추가하여 다른 설정 파일도 사용 가능
//applicaation.yml 로 전환되면서 주석처리 @PropertySource("classpath:/application.properties")
public class MyBatisConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	//Java의 Camel casing 과 DB의 Snake casing 매핑을 위한 부분
	@Bean
	//application.properties파일의 mybatis.configuration prefix로 되어 있는 설정 사용
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		
		//SqlSessionFactoryBean과 DataSource의 연결
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		//mapper 위치 지정
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		
		//Java의 Camel casing 과 DB의 Snake casing 매핑을 위한 연결
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
