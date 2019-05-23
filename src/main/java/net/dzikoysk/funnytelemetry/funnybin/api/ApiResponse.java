package net.dzikoysk.funnytelemetry.funnybin.api;

public class ApiResponse
{
    private String fullUrl;
    private String shortUrl;
    private String uuid;

    public ApiResponse()
    {
    }

    public ApiResponse(final String fullUrl, final String shortUrl, final String uuid)
    {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.uuid = uuid;
    }

    public String getFullUrl()
    {
        return this.fullUrl;
    }

    public void setFullUrl(final String fullUrl)
    {
        this.fullUrl = fullUrl;
    }

    public String getShortUrl()
    {
        return this.shortUrl;
    }

    public void setShortUrl(final String shortUrl)
    {
        this.shortUrl = shortUrl;
    }

    public String getUuid()
    {
        return this.uuid;
    }

    public void setUuid(final String uuid)
    {
        this.uuid = uuid;
    }
}
