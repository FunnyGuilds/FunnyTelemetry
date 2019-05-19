package net.dzikoysk.funnytelemetry.funnybin.api;

public class ApiResponse
{
    private String fullUrl;
    private String shortUrl;

    public ApiResponse()
    {
    }

    public ApiResponse(final String fullUrl, final String shortUrl)
    {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
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
}
