package net.dzikoysk.funnytelemetry.commons;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base class for all database entities
 */
@MappedSuperclass
public class EntityWithUniqueId
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, columnDefinition = "BINARY(16)")
    private UUID uniqueId = UUID.randomUUID();

    public EntityWithUniqueId()
    {
    }

    public EntityWithUniqueId(final UUID uniqueId)
    {
        this.uniqueId = uniqueId;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(final int id)
    {
        this.id = id;
    }

    public UUID getUniqueId()
    {
        return this.uniqueId;
    }

    public void setUniqueId(final UUID uniqueId)
    {
        this.uniqueId = uniqueId;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || this.getClass() != o.getClass())
        {
            return false;
        }
        EntityWithUniqueId entity = (EntityWithUniqueId) o;
        return Objects.equals(this.uniqueId, entity.uniqueId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.uniqueId);
    }
}
