package net.dzikoysk.funnytelemetry.panel.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginLogger implements ApplicationListener<AuthenticationSuccessEvent>
{
    private final LogService logService;

    @Autowired
    public LoginLogger(final LogService logService)
    {
        this.logService = logService;
    }

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event)
    {
        this.logService.submitLog(ActionType.LOGIN, null, event.getAuthentication().getName(), ((OAuth2AuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress());
    }
}
