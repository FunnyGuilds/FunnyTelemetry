package net.dzikoysk.funnytelemetry.shortlink.impl;

import java.util.Random;

import net.dzikoysk.funnytelemetry.shortlink.ShortLinkService;
import org.springframework.beans.factory.annotation.Value;

public abstract class ShortLinkServiceImpl implements ShortLinkService
{
    private static final char[] CHARACTER_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".toCharArray();

    private final Random random = new Random();

    @Value("${funnytelemetry.short-link.protocol}")
    private String protocol;

    @Value("${funnytelemetry.short-link.domain}")
    private String domain;

    @Override
    public String getProtocol()
    {
        return this.protocol;
    }

    @Override
    public String getLinkDomain()
    {
        return this.domain;
    }

    @Override
    public String generateShortCode()
    {
        String code;

        do
        {
            final char[] array = new char[this.getLinkLength()];

            for (int i = 0; i < array.length; i++)
            {
                array[i] = CHARACTER_POOL[this.random.nextInt(CHARACTER_POOL.length)];
            }

            code = new String(array);
        }
        while (this.isCodeUsed(code));

        return code;
    }

    @Override
    public String getShortLink(final String code)
    {
        return this.getProtocol() + "://" + this.getLinkDomain() + this.getShortLinkPath() + code;
    }
}
