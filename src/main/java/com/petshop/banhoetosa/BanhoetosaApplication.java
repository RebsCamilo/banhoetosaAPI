package com.petshop.banhoetosa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
//@EnableSpringDataWebSupport //modulo que habilita receber o Pageable no path
public class BanhoetosaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanhoetosaApplication.class, args);
	}

}
