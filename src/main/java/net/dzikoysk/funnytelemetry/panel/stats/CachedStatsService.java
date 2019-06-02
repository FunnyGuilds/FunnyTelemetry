package net.dzikoysk.funnytelemetry.panel.stats;

public interface CachedStatsService
{
    Stats getStats();

    void forceUpdate();
}
