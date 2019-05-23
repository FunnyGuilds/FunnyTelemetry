package net.dzikoysk.funnytelemetry.logs.impl.resolvers;

import net.dzikoysk.funnytelemetry.logs.ActionType;
import net.dzikoysk.funnytelemetry.logs.LogDetailResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BannedAndRevokedIpResolver implements LogDetailResolver
{
    @Value("${funnytelemetry.main}")
    private String mainUrl;

    @Override
    public boolean canResolve(final ActionType actionType)
    {
        return actionType == ActionType.BANNED_IP || actionType == ActionType.REVOKED_BAN;
    }

    @Override
    public String resolve(final String details)
    {
        final String url = this.mainUrl + "/panel/bans/" + details;
        return String.format("<a href=\"%1$s\">%2$s</a>",  url, details);
    }
}
