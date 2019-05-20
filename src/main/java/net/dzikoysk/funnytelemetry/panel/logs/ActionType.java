package net.dzikoysk.funnytelemetry.panel.logs;

public enum ActionType
{
    LOGIN("Logged in"),
    HIDE_PASTE("Hid paste"),
    CHANGE_ACCESS("Changed access"),
    USED_SETUP("Used setup");

    private String text;

    ActionType(final String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return this.text;
    }
}
