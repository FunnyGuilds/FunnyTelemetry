package net.dzikoysk.funnytelemetry.panel.stats;

public interface StatsService
{
    long[] getPastesSubmittedInRange(StatsRange range);

    long[] getBundlesSubmittedInRange(StatsRange range);

    long getPastesToday();

    long getBundlesToday();

    long getPastesTotal();

    long getBundlesTotal();
}
