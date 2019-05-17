package net.dzikoysk.funnytelemetry.funnybin.api;

public class ApiResponse
{
    private String url;

    public ApiResponse()
    {
    }

    public ApiResponse(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
