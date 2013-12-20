package net.sample.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration @ComponentScan(basePackages = {"net.sample.app"})
public class AppConfig extends WebMvcConfigurerAdapter {
	
}
