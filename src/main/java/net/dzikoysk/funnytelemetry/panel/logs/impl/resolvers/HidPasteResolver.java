package net.dzikoysk.funnytelemetry.panel.logs.impl.resolvers;

import net.dzikoysk.funnytelemetry.panel.logs.ActionType;
import net.dzikoysk.funnytelemetry.panel.logs.LogDetailResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HidPasteResolver implements LogDetailResolver
{
    @Value("${funnytelemetry.main}")
    private String mainUrl;

    @Override
    public boolean canResolve(final ActionType actionType)
    {
        return actionType == ActionType.HIDE_PASTE;
    }

    @Override
    public String resolve(final String details)
    {
        final String url = this.mainUrl + "/panel/funnybin/paste/" + details;
        return String.format("<a href=\"%1$s\">%2$s</a>",  url, details);
    }
}
