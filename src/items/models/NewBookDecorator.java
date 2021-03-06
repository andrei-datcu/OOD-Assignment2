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

    @Override
    public int computeFine() {
        int dueDays = DateUtils.daysBetween(getDecoratedBook().getDueDate().getTimeInMillis(),
                                            System.currentTimeMillis());

        if (dueDays == 0)
            return 0;

        return 25 + 10 * (dueDays - 1);
    }

    /**
     * Accept method for the visitor pattern
     *
     * @param visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(getDecoratedBook());
    }
}
