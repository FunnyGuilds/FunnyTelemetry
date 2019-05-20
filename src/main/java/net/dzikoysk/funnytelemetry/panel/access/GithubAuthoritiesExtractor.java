package net.dzikoysk.funnytelemetry.panel.access;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.dzikoysk.funnytelemetry.github.GithubApiService;
import net.dzikoysk.funnytelemetry.panel.AccessLevel;
import net.dzikoysk.funnytelemetry.panel.logs.ActionType;
import net.dzikoysk.funnytelemetry.panel.logs.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class GithubAuthoritiesExtractor implements AuthoritiesExtractor
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GithubAuthoritiesExtractor.class);

    private final GithubApiService   githubApiService;
    private final PanelAccessService panelAccessService;

    @Autowired
    public GithubAuthoritiesExtractor(final GithubApiService githubApiService, final PanelAccessService panelAccessService)
    {
        this.githubApiService = githubApiService;
        this.panelAccessService = panelAccessService;
    }

    @Override
    public List<GrantedAuthority> extractAuthorities(final Map<String, Object> map)
    {
        final String login = (String) map.get("login");

        Collection<String> organizations;
        try
        {
            organizations = this.githubApiService.getUsersOrganizations(login);
        }
        catch (final IllegalArgumentException e)
        {
            LOGGER.warn("Failed to get authorities for " + login, e);
            organizations = Collections.emptyList();
        }

        final AccessLevel access = this.panelAccessService.getAccessForUserOrOrganizations(login, organizations);

        return access.getRoles()
                     .stream()
                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toList());
    }
}
