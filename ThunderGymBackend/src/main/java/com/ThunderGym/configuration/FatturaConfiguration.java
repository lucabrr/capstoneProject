package com.ThunderGym.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ThunderGym.classes.Fattura;

@Configuration
public class FatturaConfiguration {
	@Bean
	@Scope("prototype")
Fattura fattura() {
	return new Fattura();
}
}
