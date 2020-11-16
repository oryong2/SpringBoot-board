package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.interceptor.ReqStartEndLoggingInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ReqStartEndLoggingInterceptor());
	}
	
	//파일 Up/Download를 위한 Apache Common Fileupload 구현체로 MultipartResolver 등록
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonMultipartResolver = new CommonsMultipartResolver();
		commonMultipartResolver.setDefaultEncoding("UTF-8");
		//Upload 파일 크기 제한 5MB
		commonMultipartResolver.setMaxUploadSize(5*1024*1024);
		return commonMultipartResolver;
	}
	

		

}
