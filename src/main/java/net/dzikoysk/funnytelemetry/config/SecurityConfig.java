package net.dzikoysk.funnytelemetry.config;

import net.dzikoysk.funnytelemetry.panel.access.PanelAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@EnableOAuth2Sso
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Value("${funnytelemetry.short-link.domain}")
    private String shortLinkDomain;

    @Override
    protected void configure(final HttpSecurity http) throws Exception
    {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers("/funnybin/api/**", "/funnytrace/api/**")
                    .permitAll()
                .antMatchers( "/", "/login", "/resources/**")
                    .permitAll()
                .antMatchers("/setup", "/panel/no-access")
                    .fullyAuthenticated()
                .antMatchers("/panel/funnybin/pastes/hidden", "/panel/funnybin/paste/*/hide")
                    .hasRole("ADMIN")
                .antMatchers("/panel/logs/mine")
                    .hasRole("USER")
                .antMatchers("/panel/access/**", "/panel/logs/**", "/panel/bans/**")
                    .hasRole("ADMIN")
                .antMatchers("/panel/**")
                    .hasRole("USER")
                .requestMatchers(new RequestHeaderRequestMatcher("Host", this.shortLinkDomain))
                    .permitAll()
                .anyRequest()
                    .authenticated()
            .and()
            .logout()
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
            .exceptionHandling()
                .defaultAccessDeniedHandlerFor(new PanelAccessDeniedHandler(), new AntPathRequestMatcher("/panel/**"))
            .and()
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/funnybin/api/**")
            .and()
            .sessionManagement()
                .maximumSessions(1000)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(this.sessionRegistry());

        // @formatter:on
    }

    @Bean
    public SessionRegistry sessionRegistry()
    {
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher()
    {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}
