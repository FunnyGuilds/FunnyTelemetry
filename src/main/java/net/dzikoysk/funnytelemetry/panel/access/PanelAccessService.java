package net.dzikoysk.funnytelemetry.panel.access;

import java.util.Collection;

import net.dzikoysk.funnytelemetry.panel.AccessLevel;

public interface PanelAccessService
{
    void setAccessForUser(String user, AccessLevel AccessLevel);

    void setAccessForOrganization(String organization, AccessLevel AccessLevel);

    AccessLevel getAccessForUser(String user);

    AccessLevel getAccessForOrganization(String organization);

    AccessLevel getAccessForUserOrOrganizations(String user, Collection<String> organizations);
}
