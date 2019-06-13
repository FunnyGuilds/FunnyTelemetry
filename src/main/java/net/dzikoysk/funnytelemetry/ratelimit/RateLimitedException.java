package net.dzikoysk.funnytelemetry.ratelimit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitedException extends RuntimeException
{
    public RateLimitedException()
    {
        super();
    }

    public RateLimitedException(final String message)
    {
        super(message);
    }

    public RateLimitedException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public RateLimitedException(final Throwable cause)
    {
        super(cause);
    }
}
