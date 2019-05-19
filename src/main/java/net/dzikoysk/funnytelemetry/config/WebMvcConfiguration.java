package net.dzikoysk.funnytelemetry.config;

import java.util.List;
import javax.servlet.Filter;

import net.dzikoysk.funnytelemetry.shortlink.ShortLinkHandlerInterceptor;
import net.dzikoysk.funnytelemetry.shortlink.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport
{
    private final ApplicationContext applicationContext;

    @Value("${funnytelemetry.debug.resources_root:}")
    private String resourcesRoot;

    @Value("${funnytelemetry.debug.templates_root:}")
    private String templatesRoot;

    @Autowired
    public WebMvcConfiguration(final ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void addResourceHandlers(final ResourceHandlerRegistry registry)
    {
        registry
            .addResourceHandler("/resources/**")
            .addResourceLocations(this.resourcesRoot.isEmpty() ? "classpath:/panel/resources/" : this.resourcesRoot)
            .resourceChain(this.resourcesRoot.isEmpty());
    }

    @Override
    protected void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers)
    {
        final PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public FilterRegistrationBean securityFilterChain(@Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) final Filter securityFilter)
    {
        final FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>(securityFilter);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        return registration;
    }

    @Bean
    public FilterRegistrationBean shortLinkHandlerInterceptor()
    {
        final FilterRegistrationBean<ShortLinkHandlerInterceptor> registrationBean = new FilterRegistrationBean<>();
        final ShortLinkHandlerInterceptor userFilter = new ShortLinkHandlerInterceptor(this.applicationContext.getBeansOfType(ShortLinkService.class).values());
        registrationBean.setFilter(userFilter);
        registrationBean.setOrder(Integer.MAX_VALUE);
        return registrationBean;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver()
    {
        final var templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix(this.templatesRoot.isEmpty() ? "classpath:/panel/views/" : this.templatesRoot);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(this.templatesRoot.isEmpty());
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine()
    {
        final var templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(this.templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    @Bean
    public RequestContextListener requestContextListener()
    {
        return new RequestContextListener();
    }
}
