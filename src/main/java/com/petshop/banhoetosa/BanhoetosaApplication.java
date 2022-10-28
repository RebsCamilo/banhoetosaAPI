package com.petshop.banhoetosa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Service;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(title = "Api de Petshop", version = "1.0.0", description = "Api para gestão do Banho&Tosa de um Petshop"),
	servers = @Server(url = "http://localhost:8080")
)
public class BanhoetosaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanhoetosaApplication.class, args);
	}

}
