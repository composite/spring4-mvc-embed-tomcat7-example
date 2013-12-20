package net.sample.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration @EnableWebMvc @EnableWebSocket @ComponentScan(basePackages = {"net.sample.app"})
public class ServletConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/js/**").addResourceLocations("/js/**").setCachePeriod(31556926);
		registry.addResourceHandler("/static/css/**").addResourceLocations("/css/**").setCachePeriod(31556926);
		registry.addResourceHandler("/static/resource/**").addResourceLocations("/resource/**").setCachePeriod(31556926);
	}
	
	@Bean
	public UrlBasedViewResolver urlBasedViewResolver(){
		UrlBasedViewResolver urlBasedViewResolver = new InternalResourceViewResolver();
        urlBasedViewResolver.setViewClass(JstlView.class);
        urlBasedViewResolver.setPrefix("/views/");
        urlBasedViewResolver.setSuffix(".jsp");
        return urlBasedViewResolver;
	}
}
