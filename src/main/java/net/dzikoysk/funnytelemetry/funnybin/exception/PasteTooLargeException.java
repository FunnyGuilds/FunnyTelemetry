package net.dzikoysk.funnytelemetry.funnybin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
public class PasteTooLargeException extends RuntimeException
{
    public PasteTooLargeException()
    {
        super();
    }

    public PasteTooLargeException(final String message)
    {
        super(message);
    }

    public PasteTooLargeException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public PasteTooLargeException(final Throwable cause)
    {
        super(cause);
    }
}
