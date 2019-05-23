package net.dzikoysk.funnytelemetry.logs;

public interface LogDetailResolver
{
    boolean canResolve(ActionType actionType);

    String resolve(String details);
}
