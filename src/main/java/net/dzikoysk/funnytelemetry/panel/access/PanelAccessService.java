package net.dzikoysk.funnytelemetry.panel.access;

import java.util.Collection;
import java.util.Map;

import net.dzikoysk.funnytelemetry.panel.AccessLevel;

public interface PanelAccessService
{
    void setAccessForUser(String user, AccessLevel AccessLevel);

    void setAccessForOrganization(String organization, AccessLevel AccessLevel);

    AccessLevel getAccessForUser(String user);

    AccessLevel getAccessForOrganization(String organization);

    AccessLevel getAccessForUserOrOrganizations(String user, Collection<String> organizations);

    Map<String, AccessLevel> getAccessLevelForUsers();

    Map<String, AccessLevel> getAccessLevelForOrganizations();

    void reorganizeAccess(final Map<String, AccessLevel> userAccess, final Map<String, AccessLevel> organizationAccess);
}
