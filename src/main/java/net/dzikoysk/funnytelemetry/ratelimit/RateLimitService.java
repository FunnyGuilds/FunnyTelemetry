package net.dzikoysk.funnytelemetry.ratelimit;

public interface RateLimitService
{
    boolean checkRateLimit(String address);

    void bumpRateLimit(String address);

    void ensureNotRateLimited(String addresss);

    void removeChanges(String address);

    int getMaxRateLimit();

    RateLimit getCurrentLateLimit(String address);
}
