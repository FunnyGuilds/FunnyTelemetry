package net.dzikoysk.funnytelemetry.panel.logs;

public interface LogDetailResolver
{
    boolean canResolve(ActionType actionType);

    String resolve(String details);
}
