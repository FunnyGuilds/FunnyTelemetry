package net.dzikoysk.funnytelemetry.funnybin;

public enum PasteType
{
    LOGS("Logs"),
    CONFIG("Configuration"),
    OTHER("Other");

    private final String name;

    PasteType(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
