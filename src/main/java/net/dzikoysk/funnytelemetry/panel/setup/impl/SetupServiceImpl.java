package net.dzikoysk.funnytelemetry.panel.setup.impl;

import java.security.Principal;

import net.dzikoysk.funnytelemetry.panel.AccessLevel;
import net.dzikoysk.funnytelemetry.panel.access.PanelAccessService;
import net.dzikoysk.funnytelemetry.panel.setup.SetupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SetupServiceImpl implements SetupService
{
    private final PanelAccessService panelAccessService;

    @Value("${funnytelemetry.setup-password:}")
    private String password;

    public SetupServiceImpl(final PanelAccessService panelAccessService)
    {
        this.panelAccessService = panelAccessService;
    }

    @Override
    public boolean isSetupAvailable()
    {
        return ! StringUtils.isEmpty(this.password);
    }

    @Override
    public boolean validatePassword(final String password)
    {
        return this.isSetupAvailable() && this.password.equals(password);
    }

    @Override
    public void setupUser(final Principal user)
    {
        this.panelAccessService.setAccessForUser(user.getName(), AccessLevel.ADMIN_ACCESS);
    }
}
