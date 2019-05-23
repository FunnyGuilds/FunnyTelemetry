package net.dzikoysk.funnytelemetry.panel.bans;

public class RevokeForm
{
    private String reason;

    public RevokeForm()
    {
    }

    public RevokeForm(final String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return this.reason;
    }

    public void setReason(final String reason)
    {
        this.reason = reason;
    }
}
