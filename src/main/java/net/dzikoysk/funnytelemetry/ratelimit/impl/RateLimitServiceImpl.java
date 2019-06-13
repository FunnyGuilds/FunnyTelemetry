package net.dzikoysk.funnytelemetry.ratelimit.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.transaction.Transactional;

import net.dzikoysk.funnytelemetry.ban.BanService;
import net.dzikoysk.funnytelemetry.funnybin.PasteService;
import net.dzikoysk.funnytelemetry.ratelimit.RateLimit;
import net.dzikoysk.funnytelemetry.ratelimit.RateLimitService;
import net.dzikoysk.funnytelemetry.ratelimit.RateLimitedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RateLimitServiceImpl implements RateLimitService
{
    private final List<RateLimit> rateLimits = new ArrayList<>();
    private final BanService      banService;
    private final PasteService    pasteService;

    @Value("${funnytelemetry.limits.max-request-per-hour}")
    private int maxRequestsPerHour;

    @Value("${funnytelemetry.limits.spam-autoban}")
    private int spamAutoban;

    @Autowired
    public RateLimitServiceImpl(final BanService banService, final PasteService pasteService)
    {
        this.banService = banService;
        this.pasteService = pasteService;
    }

    @Override
    public boolean checkRateLimit(final String address)
    {
        return this.getCurrentLateLimit(address).getLimit() < this.maxRequestsPerHour;
    }

    @Override
    public void bumpRateLimit(final String address)
    {
        final RateLimit limit = this.getCurrentLateLimit(address);
        limit.setLimit(limit.getLimit() + 1);

        if (limit.getLimit() > this.spamAutoban)
        {
            this.removeChanges(address);
            this.banService.ban(address, "Sent over " + this.spamAutoban + " requests in an hour.", "Rate limit autoban");
        }
    }

    @Override
    public void ensureNotRateLimited(final String address)
    {
        this.bumpRateLimit(address);

        if (! this.checkRateLimit(address))
        {
            throw new RateLimitedException();
        }
    }

    @Override
    @Transactional
    public void removeChanges(final String address)
    {
        final Date after = new Date(this.getCurrentLateLimit(address).getClearsAt() - TimeUnit.HOURS.toMillis(1));

        this.pasteService.getPastesAfter(after, address).forEach(this.pasteService::hide);
        this.pasteService.getBundlesAfter(after, address).forEach(this.pasteService::hide);
    }

    @Override
    public int getMaxRateLimit()
    {
        return this.maxRequestsPerHour;
    }

    @Override
    public RateLimit getCurrentLateLimit(final String address)
    {
        for (final RateLimit rateLimit : this.rateLimits)
        {
            if (rateLimit.getAddress().equals(address))
            {
                return this.validate(rateLimit);
            }
        }

        final RateLimit limit = new RateLimit(address, 0, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
        this.rateLimits.add(limit);
        return limit;
    }

    private RateLimit validate(final RateLimit rateLimit)
    {
        if (rateLimit.getClearsAt() < System.currentTimeMillis())
        {
            rateLimit.setClearsAt(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
            rateLimit.setLimit(0);
        }

        return rateLimit;
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void clearRates()
    {
        for (final RateLimit rateLimit : this.rateLimits)
        {
            this.validate(rateLimit);
        }
    }
}
