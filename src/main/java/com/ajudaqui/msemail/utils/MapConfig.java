package com.ajudaqui.msemail.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapConfig {
	@Bean
	public ModelMapper obterModelMapper() {
		return new ModelMapper();
	}

}
