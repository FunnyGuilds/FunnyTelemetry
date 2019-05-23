package net.dzikoysk.funnytelemetry.ratelimit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimittedException extends RuntimeException
{
    public RateLimittedException()
    {
        super();
    }

    public RateLimittedException(final String message)
    {
        super(message);
    }

    public RateLimittedException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public RateLimittedException(final Throwable cause)
    {
        super(cause);
    }
}
