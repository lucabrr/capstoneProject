package com.ThunderGym.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ThunderGym.classes.Cliente;

@Configuration
public class ClienteConfiguration {
	@Bean
	@Scope("prototype")
	Cliente cliente() {
		return new Cliente();
	}

	
	
}
