package net.dzikoysk.funnytelemetry.ratelimit;

public class RateLimit
{
    private final String address;
    private int limit;
    private long clearsAt;

    public RateLimit(final String address, final int limit, final long clearsAt)
    {
        this.address = address;
        this.limit = limit;
        this.clearsAt = clearsAt;
    }

    public String getAddress()
    {
        return this.address;
    }

    public int getLimit()
    {
        return this.limit;
    }

    public void setLimit(final int limit)
    {
        this.limit = limit;
    }

    public long getClearsAt()
    {
        return this.clearsAt;
    }

    public void setClearsAt(final long clearsAt)
    {
        this.clearsAt = clearsAt;
    }
}
