package net.sample.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.spring.template.SpringTemplateLoader;
import de.neuland.jade4j.spring.view.JadeViewResolver;

@Configuration @Import({WebsocketConfig.class})
@EnableWebMvc
@ComponentScan(basePackages = {"net.sample.app"})
@PropertySource("classpath:sample.properties")
public class ServletConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(31556926);
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/resource/**").addResourceLocations("classpath:/static/resource/").setCachePeriod(31556926);
	}
	
	@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true);
        Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        mediaTypes.put("xml", MediaType.APPLICATION_XML);
        mediaTypes.put("xml2", MediaType.TEXT_XML);
        mediaTypes.put("xhtml", MediaType.APPLICATION_XHTML_XML);
        mediaTypes.put("html", MediaType.TEXT_HTML);
        configurer.mediaTypes(mediaTypes);
        configurer.defaultContentType(MediaType.TEXT_HTML);
    }
	
	// Only needed if we are using @Value and ${...} when referencing properties
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[] { new ClassPathResource("sample.properties") };
        propertySources.setLocations(resources);
        propertySources.setIgnoreUnresolvablePlaceholders(true);
        return propertySources;
    }
	
	// Provides internationalization of messages
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
	
	///////////////////////////
	// BeanNameViewResolver
	///////////////////////////
	@Bean
	public BeanNameViewResolver beanNameViewResolver(){
		BeanNameViewResolver viewResolver = new BeanNameViewResolver();
		viewResolver.setOrder(1);
		return viewResolver;
	}
	
	///////////////////////////
	// View Engine : jade
	///////////////////////////
	@Bean
    public SpringTemplateLoader templateLoader() {
        SpringTemplateLoader templateLoader = new SpringTemplateLoader();
        templateLoader.setBasePath("classpath:/views/");
        templateLoader.setEncoding("UTF-8");
        templateLoader.setSuffix(".jade");
        return templateLoader;
    }

    @Bean
    public JadeConfiguration jadeConfiguration() {
        JadeConfiguration configuration = new JadeConfiguration();
        configuration.setCaching(false);
        configuration.setTemplateLoader(templateLoader());
        return configuration;
    }

    @Bean
    public ViewResolver jadeViewResolver() {
        JadeViewResolver viewResolver = new JadeViewResolver();
        viewResolver.setConfiguration(jadeConfiguration());
        viewResolver.setOrder(2);
        return viewResolver;
    }
	
	///////////////////////////
	// View Engine : Thymeleaf
	///////////////////////////
    @Bean
    public TemplateResolver templateResolver(){
    	ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    	templateResolver.setPrefix("views/");
    	templateResolver.setSuffix(".html");
    	templateResolver.setTemplateMode("HTML5");
    	templateResolver.setCharacterEncoding("UTF-8");
    	templateResolver.setCacheable(false);
    	return templateResolver;
    }
    @Bean
    public SpringTemplateEngine springTemplateEngine(){
    	SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
    	springTemplateEngine.setTemplateResolver(templateResolver());
    	return springTemplateEngine;
    }
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(){
    	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    	viewResolver.setTemplateEngine(springTemplateEngine());
    	viewResolver.setOrder(3);
    	return viewResolver;
    }
}
