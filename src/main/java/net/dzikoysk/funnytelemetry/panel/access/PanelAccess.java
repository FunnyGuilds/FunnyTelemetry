package net.dzikoysk.funnytelemetry.panel.access;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import net.dzikoysk.funnytelemetry.commons.EntityWithUniqueId;
import net.dzikoysk.funnytelemetry.panel.AccessLevel;

@Entity
@Table(name = "funnytelemetry_panel_access")
public class PanelAccess extends EntityWithUniqueId
{
    @Column(unique = true)
    public String name;

    @Column(columnDefinition = "VARCHAR(16)", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    public PanelAccess()
    {
    }

    public PanelAccess(final UUID uniqueId, final String name, final AccessLevel accessLevel)
    {
        super(uniqueId);
        this.name = name;
        this.accessLevel = accessLevel;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public AccessLevel getAccessLevel()
    {
        return this.accessLevel;
    }

    public void setAccessLevel(final AccessLevel accessLevel)
    {
        this.accessLevel = accessLevel;
    }
}
