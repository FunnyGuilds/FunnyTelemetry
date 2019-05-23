package net.dzikoysk.funnytelemetry.funnybin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasteInvalidRequest extends RuntimeException
{
    public PasteInvalidRequest()
    {
        super();
    }

    public PasteInvalidRequest(final String message)
    {
        super(message);
    }

    public PasteInvalidRequest(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public PasteInvalidRequest(final Throwable cause)
    {
        super(cause);
    }
}
