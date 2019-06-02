package net.dzikoysk.funnytelemetry.panel.stats;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Stats
{
    private final Date       date;
    private final StatsRange range;
    private final long[]     pastesSubmitted;
    private final long[]     bundlesSubmitted;
    private final long       pastesToday;
    private final long       bundlesToday;
    private final long       pastesTotal;
    private final long       bundlesTotal;

    public Stats(final Date date, final StatsRange range, final long[] pastesSubmitted, final long[] bundlesSubmitted, final long pastesToday, final long bundlesToday, final long pastesTotal, final long bundlesTotal)
    {
        this.date = date;
        this.range = range;
        this.pastesSubmitted = pastesSubmitted;
        this.bundlesSubmitted = bundlesSubmitted;
        this.pastesToday = pastesToday;
        this.bundlesToday = bundlesToday;
        this.pastesTotal = pastesTotal;
        this.bundlesTotal = bundlesTotal;
    }

    public Date getDate()
    {
        return this.date;
    }

    public StatsRange getRange()
    {
        return this.range;
    }

    public long[] getPastesSubmitted()
    {
        return this.pastesSubmitted;
    }

    public long[] getBundlesSubmitted()
    {
        return this.bundlesSubmitted;
    }

    public long getPastesToday()
    {
        return this.pastesToday;
    }

    public long getBundlesToday()
    {
        return this.bundlesToday;
    }

    public long getPastesTotal()
    {
        return this.pastesTotal;
    }

    public long getBundlesTotal()
    {
        return this.bundlesTotal;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || this.getClass() != o.getClass())
        {
            return false;
        }
        final Stats stats = (Stats) o;
        return this.pastesToday == stats.pastesToday &&
               this.bundlesToday == stats.bundlesToday &&
               this.pastesTotal == stats.pastesTotal &&
               this.bundlesTotal == stats.bundlesTotal &&
               Objects.equals(this.date, stats.date) &&
               Objects.equals(this.range, stats.range) &&
               Arrays.equals(this.pastesSubmitted, stats.pastesSubmitted) &&
               Arrays.equals(this.bundlesSubmitted, stats.bundlesSubmitted);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(this.date, this.range, this.pastesToday, this.bundlesToday, this.pastesTotal, this.bundlesTotal);
        result = 31 * result + Arrays.hashCode(this.pastesSubmitted);
        result = 31 * result + Arrays.hashCode(this.bundlesSubmitted);
        return result;
    }

    @Override
    public String toString()
    {
        return "Stats{" +
               "date=" + this.date +
               ", range=" + this.range +
               ", pastesSubmitted=" + Arrays.toString(this.pastesSubmitted) +
               ", bundlesSubmitted=" + Arrays.toString(this.bundlesSubmitted) +
               ", pastesToday=" + this.pastesToday +
               ", bundlesToday=" + this.bundlesToday +
               ", pastesTotal=" + this.pastesTotal +
               ", bundlesTotal=" + this.bundlesTotal +
               '}';
    }
}
