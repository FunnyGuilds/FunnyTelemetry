package net.dzikoysk.funnytelemetry.panel.stats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class StatsRange
{
    private final Calendar start;
    private final Calendar end;

    public StatsRange(final Calendar startDay, final Calendar endDay)
    {
        this.start = this.normalize(startDay, true);
        this.end = this.normalize(endDay, false);

        if (this.start.getTimeInMillis() > this.end.getTimeInMillis())
        {
            throw new IllegalArgumentException("startDay > endDay");
        }
    }

    private Calendar normalize(final Calendar date, final boolean start)
    {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, start ? 0 : 23);
        calendar.set(Calendar.MINUTE, start ? 0 : 59);
        calendar.set(Calendar.SECOND, start ? 0 : 59);
        calendar.set(Calendar.MILLISECOND, start ? 0 : 999);
        return calendar;
    }

    public Calendar getStart()
    {
        return this.start;
    }

    public Calendar getEnd()
    {
        return this.end;
    }

    public List<String> getLabels()
    {
        final SimpleDateFormat format = new SimpleDateFormat("d MMM", Locale.ENGLISH);
        Calendar currentCalendar = Calendar.getInstance();
        format.setTimeZone(currentCalendar.getTimeZone());

        final List<String> labels = new ArrayList<>();
        currentCalendar.setTime(this.start.getTime());

        do
        {
            labels.add(format.format(currentCalendar.getTime()));
            currentCalendar.set(Calendar.DATE, currentCalendar.get(Calendar.DATE) + 1);
        }
        while (currentCalendar.getTimeInMillis() <= this.end.getTimeInMillis());

        return labels;
    }

    public Map<Date, Date> getDates()
    {
        final Map<Date, Date> dates = new LinkedHashMap<>();

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(this.start.getTime());

        do
        {
            dates.put(this.normalize(currentCalendar, true).getTime(), this.normalize(currentCalendar, false).getTime());
            currentCalendar.set(Calendar.DATE, currentCalendar.get(Calendar.DATE) + 1);
        }
        while (currentCalendar.getTimeInMillis() <= this.end.getTimeInMillis());

        return dates;
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
        final StatsRange that = (StatsRange) o;
        return Objects.equals(this.start, that.start) &&
               Objects.equals(this.end, that.end);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.start, this.end);
    }

    @Override
    public String toString()
    {
        return "StatsRange{" +
               "start=" + this.start +
               ", end=" + this.end +
               '}';
    }

    public static StatsRange forLastXDays(int days)
    {
        final Calendar endDate = Calendar.getInstance();
        final Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DATE, startDate.get(Calendar.DATE) - days + 1);
        return new StatsRange(startDate, endDate);
    }
}
