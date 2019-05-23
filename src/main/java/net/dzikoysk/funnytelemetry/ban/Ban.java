package net.dzikoysk.funnytelemetry.ban;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.dzikoysk.funnytelemetry.commons.EntityWithUniqueId;

@Entity
@Table(name = "funnytelemetry_bans")
public class Ban extends EntityWithUniqueId
{
    @Column(nullable = false, columnDefinition = "VARCHAR(15)")
    private String ip;

    @Column(nullable = false)
    private String issuer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean revoked;

    @Column
    private String revokedBy;

    @Column(columnDefinition = "TEXT")
    private String revocationReason;

    public Ban()
    {
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date revocationDate;

    public Ban(final UUID uniqueId, final String ip, final String issuer, final String reason, final Date date, final boolean revoked, final String revokedBy, final String revocationReason, final Date revocationDate)
    {
        super(uniqueId);
        this.ip = ip;
        this.issuer = issuer;
        this.reason = reason;
        this.date = date;
        this.revoked = revoked;
        this.revokedBy = revokedBy;
        this.revocationReason = revocationReason;
        this.revocationDate = revocationDate;
    }

    public String getIp()
    {
        return this.ip;
    }

    public void setIp(final String ip)
    {
        this.ip = ip;
    }

    public String getIssuer()
    {
        return this.issuer;
    }

    public void setIssuer(final String issuer)
    {
        this.issuer = issuer;
    }

    public String getReason()
    {
        return this.reason;
    }

    public void setReason(final String reason)
    {
        this.reason = reason;
    }

    public boolean isRevoked()
    {
        return this.revoked;
    }

    public void setRevoked(final boolean revoked)
    {
        this.revoked = revoked;
    }

    public String getRevokedBy()
    {
        return this.revokedBy;
    }

    public void setRevokedBy(final String revokedBy)
    {
        this.revokedBy = revokedBy;
    }

    public String getRevocationReason()
    {
        return this.revocationReason;
    }

    public void setRevocationReason(final String revocationReason)
    {
        this.revocationReason = revocationReason;
    }

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(final Date date)
    {
        this.date = date;
    }

    public Date getRevocationDate()
    {
        return this.revocationDate;
    }

    public void setRevocationDate(final Date revocationDate)
    {
        this.revocationDate = revocationDate;
    }

    public String getReasonShort()
    {
        return this.reason.contains("\n") ? (this.reason.split("\n")[0] + "...") : this.reason;
    }
}
