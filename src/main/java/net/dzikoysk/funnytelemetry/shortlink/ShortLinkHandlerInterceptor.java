package net.dzikoysk.funnytelemetry.shortlink;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShortLinkHandlerInterceptor extends HttpFilter
{
    private final Collection<ShortLinkService> services;

    public ShortLinkHandlerInterceptor(final Collection<ShortLinkService> services)
    {
        this.services = services;
    }

    @Override
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException
    {
        final URL url = new URL(request.getRequestURL().toString());
        boolean anyMatch = false;

        for (final ShortLinkService service : this.services)
        {
            if (service.getLinkDomain().equals(url.getHost()))
            {
                anyMatch = true;

                if (url.getPath().startsWith(service.getShortLinkPath()))
                {
                    final Optional<String> fullLink = service.resolveFullLink(url.getPath().substring(service.getShortLinkPath().length()));

                    if (fullLink.isPresent())
                    {
                        response.sendRedirect(fullLink.get());
                        return;
                    }
                    else
                    {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                }
            }
        }

        if (anyMatch)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        chain.doFilter(request, response);
    }
}
