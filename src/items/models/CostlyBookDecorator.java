package items.models;

import java.util.Calendar;

/**
 * Decorator for costly books
 */
public class CostlyBookDecorator extends BookDecorator {

    private final static int YEARS_TO_KEEP_COSTLY = 1;
    private Calendar dateMadeCostly;

    /**
     * Create a decorator by decorating a normal book
     *
     * @param book to be decorated
     */
    public CostlyBookDecorator(Book book) {
        super(book);
        dateMadeCostly = Calendar.getInstance();
    }

    @Override
    protected boolean stillAvailable() {
        Calendar now = Calendar.getInstance();
        Calendar limit = (Calendar) dateMadeCostly.clone();
        limit.add(Calendar.YEAR, YEARS_TO_KEEP_COSTLY);

        return now.before(limit);
    }

}
