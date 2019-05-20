package net.dzikoysk.funnytelemetry.panel.access.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import net.dzikoysk.funnytelemetry.panel.AccessLevel;
import net.dzikoysk.funnytelemetry.panel.access.PanelAccess;
import net.dzikoysk.funnytelemetry.panel.access.PanelAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanelAccessServiceImpl implements PanelAccessService
{
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z-_]{0,38}$");

    private final PanelAccessRepository panelAccessRepository;

    @Autowired
    public PanelAccessServiceImpl(final PanelAccessRepository panelAccessRepository)
    {
        this.panelAccessRepository = panelAccessRepository;
    }

    @Override
    public void setAccessForUser(final String user, final AccessLevel panelAccess)
    {
        this.setAccessFor(user, "", panelAccess);
    }

    @Override
    public void setAccessForOrganization(final String organization, final AccessLevel panelAccess)
    {
        this.setAccessFor(organization, "@", panelAccess);
    }

    private void setAccessFor(final String name, final String prefix, final AccessLevel panelAccess)
    {
        if (! NAME_PATTERN.matcher(name).matches())
        {
            throw new IllegalArgumentException("name is invalid");
        }

        final Optional<PanelAccess> foundAccess = this.panelAccessRepository.findByName(prefix + name);

        if (foundAccess.isEmpty())
        {
            if (panelAccess == AccessLevel.NO_ACCESS)
            {
                return;
            }

            this.panelAccessRepository.save(new PanelAccess(UUID.randomUUID(), prefix + name, panelAccess));
        }
        else
        {
            final PanelAccess access = foundAccess.get();
            access.setAccessLevel(panelAccess);

            if (panelAccess == AccessLevel.NO_ACCESS)
            {
                this.panelAccessRepository.delete(access);
            }
            else
            {
                this.panelAccessRepository.save(access);
            }
        }
    }

    @Override
    public AccessLevel getAccessForUser(final String user)
    {
        return this.getAccessFor(user, "");
    }

    @Override
    public AccessLevel getAccessForOrganization(final String organization)
    {
        return this.getAccessFor(organization, "@");
    }

    private AccessLevel getAccessFor(final String name, final String prefix)
    {
        if (! NAME_PATTERN.matcher(name).matches())
        {
            throw new IllegalArgumentException("name is invalid");
        }

        return this.panelAccessRepository.findByName(prefix + name).map(PanelAccess::getAccessLevel).orElse(AccessLevel.NO_ACCESS);
    }

    @Override
    public AccessLevel getAccessForUserOrOrganizations(final String user, final Collection<String> organizations)
    {
        final List<String> allIds = new ArrayList<>(organizations.size() + 1);
        allIds.add(user);
        organizations.stream().map(organization -> "@" + organization).forEach(allIds::add);
        final List<PanelAccess> accessList = this.panelAccessRepository.findByNameIn(allIds);

        return accessList.stream().map(PanelAccess::getAccessLevel).max(Comparator.naturalOrder()).orElse(AccessLevel.NO_ACCESS);
    }

    @Override
    public Map<String, AccessLevel> getAccessLevelForUsers()
    {
        return this.getAccessLevel(false);
    }

    @Override
    public Map<String, AccessLevel> getAccessLevelForOrganizations()
    {
        return this.getAccessLevel(true);
    }

    private Map<String, AccessLevel> getAccessLevel(final boolean organization)
    {
        final List<PanelAccess> access = organization ? this.panelAccessRepository.findByNameLike("@%") : this.panelAccessRepository.findByNameNotLike("@%");

        return access
            .stream()
            .filter(a -> a.getAccessLevel() != AccessLevel.NO_ACCESS)
            .collect(Collectors.toMap(a -> organization ? a.getName().substring(1) : a.getName(), PanelAccess::getAccessLevel));
    }

    @Override
    @Transactional
    public void reorganizeAccess(final Map<String, AccessLevel> userAccess, final Map<String, AccessLevel> organizationAccess)
    {
        final Collection<PanelAccess> access = new ArrayList<>(userAccess.size() + organizationAccess.size());
        userAccess.forEach((key, value) -> access.add(new PanelAccess(UUID.randomUUID(), key, value)));
        organizationAccess.forEach((key, value) -> access.add(new PanelAccess(UUID.randomUUID(), "@" + key, value)));

        for (final String name : userAccess.keySet())
        {
            if (! NAME_PATTERN.matcher(name).matches())
            {
                throw new IllegalArgumentException("name is invalid");
            }
        }
        for (final String name : organizationAccess.keySet())
        {
            if (! NAME_PATTERN.matcher(name).matches())
            {
                throw new IllegalArgumentException("name is invalid");
            }
        }

        this.panelAccessRepository.deleteAll();
        this.panelAccessRepository.findAll(); // this has to be here or the next query will crash, dont expect me to know how it works xd
        this.panelAccessRepository.saveAll(access);
    }
}
