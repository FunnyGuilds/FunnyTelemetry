package net.dzikoysk.funnytelemetry.funnybin;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.dzikoysk.funnytelemetry.commons.EntityWithUniqueId;

@Entity
@Table(name = "funnytelemetry_funnybin_paste_bundles")
public class PasteBundle extends EntityWithUniqueId
{
    @Column(nullable = false, columnDefinition = "VARCHAR(15)")
    private String submitter;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date submitDate;

    @Column(nullable = false, unique = true, columnDefinition = "CHAR(8)")
    private String shortLink;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "funnytelemetry_funnybin_paste_bundles_elements",
        joinColumns = @JoinColumn(name = "paste_id"),
        inverseJoinColumns = @JoinColumn(name = "bundle_id")
    )
    private List<Paste> pastes;

    @Column(nullable = false)
    private boolean hide = false;

    public PasteBundle()
    {
    }

    public PasteBundle(final UUID uniqueId, final String submitter, final Date submitDate, final String shortLink, final List<Paste> pastes, final boolean hide)
    {
        super(uniqueId);
        this.submitter = submitter;
        this.submitDate = submitDate;
        this.shortLink = shortLink;
        this.pastes = pastes;
        this.hide = hide;
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

    public List<Paste> getPastes()
    {
        return this.pastes;
    }

    public void setPastes(final List<Paste> pastes)
    {
        this.pastes = pastes;
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
