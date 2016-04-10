package items.models;

import items.Hold;
import member.Member;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Abstract class for decorated books
 */
public abstract class BookDecorator implements Book {
    private Book book;

    /**
     * Create a decorator by decorating a normal book
     * @param book to be decorated
     */
    public BookDecorator(Book book) {
        this.book = book;
    }

    /**
     * @return the book this decorator decorates
     */
    public Book getDecoratedBook() {
        return book;
    }

    /**
     * Determine if the book decorator should be still applied
     * or if the decorated book should be undecorated (not new or not costly anymore)
     * @return true if the decorator should still be kept
     */
    public static boolean shouldItemBeUndecorated(Item item) {
        try {
            BookDecorator decorator = (BookDecorator)item;
            return decorator.stillAvailable();
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Determine if the book decorator is still available (i.e. not new or not costly anymore)
     * @return true if the decorator should still be kept
     */
    protected abstract boolean stillAvailable();

    @Override
    public String getAuthor() {
        return book.getAuthor();
    }

    @Override
    public String getTitle() {
        return book.getTitle();
    }

    @Override
    public boolean issue(Member member) {
        return book.issue(member);
    }

    @Override
    public Member returnItem() {
        return book.returnItem();
    }

    @Override
    public boolean renew(Member member) {
        return book.renew(member);
    }

    @Override
    public void placeHold(Hold hold) {
        book.placeHold(hold);
    }

    @Override
    public boolean removeHold(String memberId) {
        return book.removeHold(memberId);
    }

    @Override
    public Hold getNextHold() {
        return book.getNextHold();
    }

    @Override
    public boolean hasHold() {
        return book.hasHold();
    }

    @Override
    public Iterator getHolds() {
        return book.getHolds();
    }

    @Override
    public String getId() {
        return book.getId();
    }

    @Override
    public Member getBorrower() {
        return book.getBorrower();
    }

    @Override
    public Calendar getDueDate() {
        return book.getDueDate();
    }

    @Override
    public Calendar getAddedDate() {
        return book.getAddedDate();
    }

    @Override
    public int computeFine() {
        return book.computeFine();
    }
}
