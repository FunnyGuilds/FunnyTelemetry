package net.dzikoysk.funnytelemetry.panel.access;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class PanelAccessDeniedHandler implements AccessDeniedHandler
{
    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) throws IOException
    {
        response.sendRedirect("/panel/no-access");
    }
}
