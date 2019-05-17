package net.dzikoysk.funnytelemetry.funnybin;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.dzikoysk.funnytelemetry.commons.EntityWithUniqueId;

/**
 * Represents a FunnyBin's paste
 */
@Entity
@Table(name = "funnybin_pastes")
public class Paste extends EntityWithUniqueId
{
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "VARCHAR(15)")
    private String submitter;

    public Paste()
    {
    }

    public Paste(final UUID uniqueId, final String content, final String submitter)
    {
        super(uniqueId);
        this.content = content;
        this.submitter = submitter;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setContent(final String content)
    {
        this.content = content;
    }

    public String getSubmitter()
    {
        return this.submitter;
    }

    public void setSubmitter(final String submitter)
    {
        this.submitter = submitter;
    }
}
