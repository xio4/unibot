package com.xiovr.unibot.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.spring4.view.ThymeleafView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * @author xio4
 * Main mvc config
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	/**
	 * Default application context
	 */
	@Autowired
	private ApplicationContext appContext;
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
	}
	// Change static resources paths
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
//		registry.addResourceHandler("/assets/**").
//			addResourceLocations("classpath:/resources/web/assets/");
		registry.addResourceHandler("/favicon.ico").
			addResourceLocations("classpath:/web/favicon.ico");
		registry.addResourceHandler("/css/**").
			addResourceLocations("classpath:/web/css/");
		registry.addResourceHandler("/images/**").
			addResourceLocations("classpath:/web/images/");
		registry.addResourceHandler("/js/**").
			addResourceLocations("classpath:/web/js/");
	}
	// Thymeleaf templates path
	// Right order!
	/**
	 * Overrided resource resolver for thymeleaf templates
	 */
	@Autowired
	SpringResourceResourceResolver resourceResolver;
	/**
	 * Overrided template resolver for thymeleaf templates
	 */
	@Autowired
	ITemplateResolver _templateResolver;
	/**
	 * Overrided template engine for thymeleaf templates
	 */
	@Autowired
	SpringTemplateEngine templateEngine;

	/**
	 * @return Resource resolver bean 
	 */
	@Bean(name="resourceResolver")
	public SpringResourceResourceResolver getResourceResolver()
	{
		SpringResourceResourceResolver srrr = new SpringResourceResourceResolver();
		srrr.setApplicationContext(appContext);
		return srrr;
	}
	/**
	 * @return Template resolver bean
	 */
	@Bean(name="_templateResolver")
	public TemplateResolver getTemplateResolver()
	{
		TemplateResolver resolver = new TemplateResolver();
		resolver.setCharacterEncoding("UTF-8");
		resolver.setResourceResolver(resourceResolver);
		resolver.setPrefix("classpath:/web/templates/");
//		resolver.setPrefix("/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		
		return resolver;
	}
	/**
	 * @return Template engine bean
	 */
	@Bean(name="templateEngine")
	public SpringTemplateEngine getTemplateEngine()
	{
		SpringTemplateEngine ste = new SpringTemplateEngine();
		ste.setTemplateResolver(_templateResolver);
		return ste;
	}
	//Views and View Resolvers in Thymeleaf
	/**
	 * @return Thymeleaf view resolver bean
	 */
	@Bean
	public ThymeleafViewResolver getThymeleafViewResolver()
	{
		ThymeleafViewResolver tvr = new ThymeleafViewResolver();
		tvr.setTemplateEngine(templateEngine);
		tvr.setOrder(1);
		tvr.setViewNames(new String[]{"*.html", "*.xhtml"});
		return tvr;
	}
	//some static variables see thymeleaf docs
	/**
	 * @return Overrided thymeleaf global view keys
	 */
	@Bean
	public ThymeleafView getThymeleafView()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("copyright", "2014 @Xio4");
		
		ThymeleafView tv = new ThymeleafView() {};
		tv.setStaticVariables(map);
		return tv;
	}
	//Thymeleaf i18n
    /**
     * @return locale resolver bean to set default locale
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }
 
    /**
     * @return locale change interceptor for change language in request
     * like hello.html?lang=en
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
 
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    // Path to messages.properties
    /**
     * @return message resource bean for set path i18n files
     */
    @Bean
    public MessageSource messageSource() {
      ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
      messageSource.setBasenames("classpath:/properties/messages", "classpath:/web/messages");
      // if true, the key of the message will be displayed if the key is not
      // found, instead of throwing a NoSuchMessageException
      messageSource.setUseCodeAsDefaultMessage(true);
      messageSource.setDefaultEncoding("UTF-8");
      // # -1 : never reload, 0 always reload
      messageSource.setCacheSeconds(0);
      return messageSource;
    }
}
