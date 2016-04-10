package items.models;

import java.util.Calendar;

/**
 * Created by vlad on 10/04/16.
 */
public class DateUtils {
    public static final long millisInDay = 24 * 60 * 60 * 1000;

    /**
     * Computes days between start and end
     * @param start - start time in millis since epoch
     * @param end - end time in millis since epoch
     * @return days between start and end
     */
    public static final int daysBetween(long start, long end) {
        int days = (int) ((end - start) / millisInDay);
        if (days < 1)
            return 0;

        return days;
    }
}
