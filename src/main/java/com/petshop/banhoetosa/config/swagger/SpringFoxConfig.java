package com.petshop.banhoetosa.config.swagger;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// Acesso pela page http://localhost:8080/swagger-ui.html/ ou http://localhost:8080/swagger-ui/
@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
//@OpenAPIDefinition(info = @Info(title = "Petshop API", version = "1.0", description = "Gerenciamento do Banho e Tosa"))
public class SpringFoxConfig { // SpringFoxConfig //SwaggerConfigurations p swagger 2.9.2 da alura

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.petshop.banhoetosa"))
//                .paths(PathSelectors.any())
                .paths(PathSelectors.ant("/**"))
                .build();
//                .ignoredParameterTypes(Usuario.class);
    }
}
