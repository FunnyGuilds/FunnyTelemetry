package net.dzikoysk.funnytelemetry.panel.logs.impl.resolvers;

import net.dzikoysk.funnytelemetry.panel.logs.ActionType;
import net.dzikoysk.funnytelemetry.panel.logs.LogDetailResolver;
import org.springframework.stereotype.Component;

@Component
public class ChangeAccessResolver implements LogDetailResolver
{
    @Override
    public boolean canResolve(final ActionType actionType)
    {
        return actionType == ActionType.CHANGE_ACCESS;
    }

    @Override
    public String resolve(final String details)
    {
        final String[] split = details.split(",");
        if (split.length == 0)
        {
            return "Removed access for everyone :(";
        }

        final StringBuilder out = new StringBuilder("<b>Organizations: </b><br>");
        this.appendPart(split, out, "org_");
        out.append("<b>Users: </b><br>");
        this.appendPart(split, out, "user_");

        return out.toString();
    }

    private void appendPart(final String[] split, final StringBuilder out, final String prefix)
    {
        for (final String part : split)
        {
            final String[] array = part.split("=");
            String who = array[0];
            if (!who.startsWith(prefix))
            {
                continue;
            }
            who = who.substring(prefix.length());

            String access = array[1];

            if ("USER_ACCESS".equals(access))
            {
                access = "User";
            }
            if ("ADMIN_ACCESS".equals(access))
            {
                access = "Admin";
            }

            out.append(who).append(": ").append(access).append("<br>");
        }
    }
}
