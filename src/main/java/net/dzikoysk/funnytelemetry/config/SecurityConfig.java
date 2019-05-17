package net.dzikoysk.funnytelemetry.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
                .antMatchers("/funnybin/api/post")
                    .permitAll()
                .antMatchers( "/login")
                    .permitAll()
                 .anyRequest()
                    .authenticated();

        // @formatter:on
    }
}
