package net.dzikoysk.funnytelemetry.panel.stats.impl;

import java.util.Date;
import java.util.Map;
import java.util.function.BiFunction;

import net.dzikoysk.funnytelemetry.funnybin.impl.PasteBundleRepository;
import net.dzikoysk.funnytelemetry.funnybin.impl.PasteRepository;
import net.dzikoysk.funnytelemetry.panel.stats.StatsRange;
import net.dzikoysk.funnytelemetry.panel.stats.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService
{
    private final PasteRepository       pasteRepository;
    private final PasteBundleRepository bundleRepository;

    @Autowired
    public StatsServiceImpl(final PasteRepository pasteRepository, final PasteBundleRepository bundleRepository)
    {
        this.pasteRepository = pasteRepository;
        this.bundleRepository = bundleRepository;
    }

    @Override
    public long[] getPastesSubmittedInRange(final StatsRange range)
    {
        return this.doCount(range, this.pasteRepository::countBySubmitDateBetween);
    }

    @Override
    public long[] getBundlesSubmittedInRange(final StatsRange range)
    {
        return this.doCount(range, this.bundleRepository::countBySubmitDateBetween);
    }

    @Override
    public long getPastesToday()
    {
        return this.getPastesSubmittedInRange(StatsRange.forLastXDays(1))[0];
    }

    @Override
    public long getBundlesToday()
    {
        return this.getBundlesSubmittedInRange(StatsRange.forLastXDays(1))[0];
    }

    @Override
    public long getPastesTotal()
    {
        return this.pasteRepository.count();
    }

    @Override
    public long getBundlesTotal()
    {
        return this.bundleRepository.count();
    }

    private long[] doCount(final StatsRange range, final BiFunction<Date, Date, Long> counter)
    {
        final Map<Date, Date> dates = range.getDates();
        final long[] array = new long[dates.size()];
        final int[] i = {0};
        dates.forEach((start, end) -> array[i[0]++] = counter.apply(start, end));
        return array;
    }
}
