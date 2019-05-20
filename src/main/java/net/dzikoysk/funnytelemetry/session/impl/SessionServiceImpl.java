package net.dzikoysk.funnytelemetry.session.impl;

import net.dzikoysk.funnytelemetry.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService
{
    private final SessionRegistry sessionRegistry;

    @Autowired
    public SessionServiceImpl(final SessionRegistry sessionRegistry)
    {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void invalidateAllRoles()
    {
        this.sessionRegistry
            .getAllPrincipals()
            .stream()
            .flatMap(principal -> this.sessionRegistry.getAllSessions(principal, false).stream())
            .forEach(SessionInformation::expireNow);
    }
}
