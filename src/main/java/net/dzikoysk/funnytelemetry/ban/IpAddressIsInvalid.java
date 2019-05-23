package net.dzikoysk.funnytelemetry.ban;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST      )
public class IpAddressIsInvalid extends RuntimeException
{
    public IpAddressIsInvalid()
    {
    }

    public IpAddressIsInvalid(final String message)
    {
        super(message);
    }

    public IpAddressIsInvalid(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public IpAddressIsInvalid(final Throwable cause)
    {
        super(cause);
    }
}
