package net.dzikoysk.funnytelemetry.funnybin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PasteAccessForbidden extends RuntimeException
{
    public PasteAccessForbidden()
    {
        super();
    }

    public PasteAccessForbidden(final String message)
    {
        super(message);
    }

    public PasteAccessForbidden(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public PasteAccessForbidden(final Throwable cause)
    {
        super(cause);
    }
}
