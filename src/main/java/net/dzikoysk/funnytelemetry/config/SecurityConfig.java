package net.dzikoysk.funnytelemetry.config;

import net.dzikoysk.funnytelemetry.panel.access.PanelAccessDeniedHandler;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableOAuth2Sso
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
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
                .antMatchers("/panel/**")
                    .hasRole("USER")
                .anyRequest()
                    .authenticated()
            .and()
            .logout()
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
                .exceptionHandling()
                    .defaultAccessDeniedHandlerFor(new PanelAccessDeniedHandler(), new AntPathRequestMatcher("/panel/**"));

        // @formatter:on
    }
}
