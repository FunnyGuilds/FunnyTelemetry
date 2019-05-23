package net.dzikoysk.funnytelemetry.panel.bans;

public class BanForm
{
    private String address;
    private String reason;

    public BanForm()
    {
    }

    public BanForm(final String address, final String reason)
    {
        this.address = address;
        this.reason = reason;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(final String address)
    {
        this.address = address;
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
