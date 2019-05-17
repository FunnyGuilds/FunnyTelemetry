package net.dzikoysk.funnytelemetry.github.impl;

class Organizations
{
    private String login;

    public Organizations()
    {
    }

    public Organizations(final String login)
    {
        this.login = login;
    }

    public String getLogin()
    {
        return this.login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }
}
