package net.dzikoysk.funnytelemetry.ban;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class IpAddressIsBannedException extends RuntimeException
{
    public IpAddressIsBannedException()
    {
    }

    public IpAddressIsBannedException(final String message)
    {
        super(message);
    }

    public IpAddressIsBannedException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public IpAddressIsBannedException(final Throwable cause)
    {
        super(cause);
    }
}
