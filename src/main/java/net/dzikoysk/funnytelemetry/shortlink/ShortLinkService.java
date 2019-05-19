package net.dzikoysk.funnytelemetry.shortlink;

import java.util.Optional;

public interface ShortLinkService
{
    String getProtocol();

    String getLinkDomain();

    String getShortLinkPath();

    String generateShortCode();

    boolean isCodeUsed(String string);

    String getShortLink(String code);

    int getLinkLength();

    Optional<String> resolveFullLink(String code);
}
