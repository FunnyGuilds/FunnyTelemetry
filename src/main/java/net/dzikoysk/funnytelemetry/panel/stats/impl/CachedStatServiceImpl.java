package net.dzikoysk.funnytelemetry.panel.stats.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import net.dzikoysk.funnytelemetry.panel.stats.CachedStatsService;
import net.dzikoysk.funnytelemetry.panel.stats.Stats;
import net.dzikoysk.funnytelemetry.panel.stats.StatsRange;
import net.dzikoysk.funnytelemetry.panel.stats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CachedStatServiceImpl implements CachedStatsService
{
    private static final long UPDATE_EVERY = TimeUnit.MINUTES.toMillis(10);

    private final StatsService statsService;
    private       Stats        stats;

    @Autowired
    public CachedStatServiceImpl(final StatsService statsService)
    {
        this.statsService = statsService;
    }

    @Override
    public Stats getStats()
    {
        this.updateIfNecessary();
        return this.stats;
    }

    @Override
    public void forceUpdate()
    {
        this.stats = null;
        this.updateIfNecessary();
    }

    private boolean requiresUpdate()
    {
        return this.stats == null || this.stats.getDate().getTime() + UPDATE_EVERY < System.currentTimeMillis();
    }

    private void updateIfNecessary()
    {
        if (! this.requiresUpdate())
        {
            return;
        }

        final StatsRange range = StatsRange.forLastXDays(6);
        this.stats = new Stats(
            new Date(),
            range,
            this.statsService.getPastesSubmittedInRange(range),
            this.statsService.getBundlesSubmittedInRange(range),
            this.statsService.getPastesToday(),
            this.statsService.getBundlesToday(),
            this.statsService.getPastesTotal(),
            this.statsService.getBundlesTotal()
        );
    }
}
