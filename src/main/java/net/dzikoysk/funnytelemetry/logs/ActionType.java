package net.dzikoysk.funnytelemetry.logs;

public enum ActionType
{
    LOGGED_IN("Logged in"),
    HID_PASTE("Hid paste"),
    HID_PASTE_BUNDLE("Hid paste bundle"),
    CHANGED_ACCESS("Changed access"),
    USED_SETUP("Used setup"),
    BANNED_IP("Banned an IP address"),
    REVOKED_BAN("Revoked a ban");

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
