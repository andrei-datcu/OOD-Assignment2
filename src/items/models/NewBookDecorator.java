package items.models;

import java.util.Calendar;

/**
 * Decorator for new books
 */
public class NewBookDecorator extends BookDecorator {

    private final static int DAYS_NEW = 90;

    /**
     * Create a decorator by decorating a normal book
     *
     * @param book to be decorated
     */
    public NewBookDecorator(Book book) {
        super(book);
    }

    @Override
    protected boolean stillAvailable() {
        Calendar now = Calendar.getInstance();
        Calendar deprecationDate = getAddedDate();
        deprecationDate.add(Calendar.DATE, DAYS_NEW);

        return now.before(deprecationDate);
    }
}
