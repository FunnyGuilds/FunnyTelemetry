package net.dzikoysk.funnytelemetry.panel.logs.impl.resolvers;

import net.dzikoysk.funnytelemetry.panel.logs.ActionType;
import net.dzikoysk.funnytelemetry.panel.logs.LogDetailResolver;
import org.springframework.stereotype.Component;

@Component
public class SetupPasteResolver implements LogDetailResolver
{
    @Override
    public boolean canResolve(final ActionType actionType)
    {
        return actionType == ActionType.USED_SETUP;
    }

    @Override
    public String resolve(final String details)
    {
        return "Gave admin to himself using the setup script";
    }
}
