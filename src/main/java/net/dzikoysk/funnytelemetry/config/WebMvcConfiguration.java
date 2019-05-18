package net.dzikoysk.funnytelemetry.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
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
