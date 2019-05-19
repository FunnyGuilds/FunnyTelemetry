package net.dzikoysk.funnytelemetry.funnybin;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date submitDate;

    @Column(nullable = false, unique = true, columnDefinition = "CHAR(8)")
    private String shortLink;

    @Column(nullable = false)
    private boolean hide = false;

    public Paste()
    {
    }

    public Paste(final UUID uniqueId, final String content, final String submitter, final Date submitDate, final String shortLink, final boolean hide)
    {
        super(uniqueId);
        this.content = content;
        this.submitter = submitter;
        this.submitDate = submitDate;
        this.shortLink = shortLink;
        this.hide = hide;
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

    public Date getSubmitDate()
    {
        return this.submitDate;
    }

    public void setSubmitDate(final Date submitDate)
    {
        this.submitDate = submitDate;
    }

    public String getShortLink()
    {
        return this.shortLink;
    }

    public void setShortLink(final String shortLink)
    {
        this.shortLink = shortLink;
    }

    public boolean isHide()
    {
        return this.hide;
    }

    public void setHide(final boolean hide)
    {
        this.hide = hide;
    }
}
