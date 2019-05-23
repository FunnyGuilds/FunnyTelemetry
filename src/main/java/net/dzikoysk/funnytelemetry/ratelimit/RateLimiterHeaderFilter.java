package net.dzikoysk.funnytelemetry.ratelimit;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class RateLimiterHeaderFilter extends OncePerRequestFilter
{
    private final RateLimitService rateLimitService;

    public RateLimiterHeaderFilter(final RateLimitService rateLimitService)
    {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException
    {
        final RateLimit rateLimit = this.rateLimitService.getCurrentLateLimit(request.getRemoteAddr());
        response.addHeader("X-RateLimit-Limit", String.valueOf(this.rateLimitService.getMaxRateLimit()));
        response.addHeader("X-RateLimit-Remaining", String.valueOf(Math.max(0, this.rateLimitService.getMaxRateLimit() - rateLimit.getLimit())));
        response.addHeader("X-RateLimit-Reset-At", String.valueOf(rateLimit.getClearsAt()));
        filterChain.doFilter(request, response);
    }
}
