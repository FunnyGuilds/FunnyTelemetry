package net.dzikoysk.funnytelemetry.funnybin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PasteNotFoundException extends RuntimeException
{
    public PasteNotFoundException()
    {
        super();
    }

    public PasteNotFoundException(final String message)
    {
        super(message);
    }

    public PasteNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public PasteNotFoundException(final Throwable cause)
    {
        super(cause);
    }
}
