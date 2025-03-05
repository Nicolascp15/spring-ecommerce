package com.curso.ecommerce;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//esto va ser un path para apuntar desde cualquier lugar a los recursos de imagenes
@Configuration
public class RessourceWebConfiguration implements WebMvcConfigurer
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) 
	{
		registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
	}
}
