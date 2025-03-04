package app.expenses_application.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration class for Swagger documentation.
 */
@Configuration
public class SwaggerConfig {

	/**
	 * Configures Docket bean for Swagger API documentation.
	 *
	 * @return the configured Docket bean.
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
				.select()
				.apis(RequestHandlerSelectors.basePackage("app.expenses_application"))
				.build()
				.apiInfo(apiInfo());
	}

	/**
	 * Builds the API information for Swagger documentation.
	 *
	 * @return the API information.
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Your API Documentation")
				.description("Description of your API.")
				.version("1.0.0")
				.build();
	}
}
