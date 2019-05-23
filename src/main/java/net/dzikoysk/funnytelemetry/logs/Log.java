package net.dzikoysk.funnytelemetry.logs;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.dzikoysk.funnytelemetry.commons.EntityWithUniqueId;

@Entity
@Table(name = "funnytelemetry_panel_logs")
public class Log extends EntityWithUniqueId
{
    @Column(nullable = false)
    private String user;

    @Column(columnDefinition = "VARCHAR(16)", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false, columnDefinition = "VARCHAR(15)")
    private String ip;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    public Log()
    {
    }

    public Log(final UUID uniqueId, final String user, final ActionType actionType, final String details, final String ip, final Date date)
    {
        super(uniqueId);
        this.user = user;
        this.actionType = actionType;
        this.details = details;
        this.ip = ip;
        this.date = date;
    }

    public String getUser()
    {
        return this.user;
    }

    public void setUser(final String user)
    {
        this.user = user;
    }

    public ActionType getActionType()
    {
        return this.actionType;
    }

    public void setActionType(final ActionType actionType)
    {
        this.actionType = actionType;
    }

    public String getDetails()
    {
        return this.details;
    }

    public void setDetails(final String details)
    {
        this.details = details;
    }

    public String getIp()
    {
        return this.ip;
    }

    public void setIp(final String ip)
    {
        this.ip = ip;
    }

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(final Date date)
    {
        this.date = date;
    }
}
