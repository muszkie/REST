package rest.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
		
	private static final String EMAIL = null;
	private static final String NAME = null;
	private static final String URL = null;

	@Bean
	public Docket api() {                
    		return new Docket(DocumentationType.SWAGGER_2)          
      			.select()
      			.apis(RequestHandlerSelectors.basePackage("rest.controllers"))
      			.build()
      			.apiInfo(apiInfo());
	}
	
	private Contact contact = new Contact(NAME, URL, EMAIL);
	
	private ApiInfo apiInfo() {
    		ApiInfo apiInfo = new ApiInfo("", null, null, null, contact, null, null);
    		return apiInfo;
	}
	
	

}
