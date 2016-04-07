package items.models;

import items.Hold;
import member.Member;

import java.io.Serializable;
import java.util.*;

/**
 * Created by andrei on 4/8/16.
 */

public abstract class BaseItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Member borrowedBy;
    private List holds = new LinkedList();
    private Calendar dueDate;

    /**
     * Creates an item with the given id
     *
     * @param id book id
     */
    public BaseItem(String id) {
        this.id = id;
    }

    /**
     * Marks the item as issued to a member
     *
     * @param member the borrower
     * @return true if the item could be issued.
     */
    public boolean issue(Member member) {
        borrowedBy = member;
        dueDate = new GregorianCalendar();
        dueDate.setTimeInMillis(System.currentTimeMillis());
        dueDate.add(Calendar.MONTH, 1);
        return true;
    }

    /**
     * Marks the item as returned
     *
     * @return The member who had borrowed the book
     */
    public Member returnItem() {
        if (borrowedBy == null) {
            return null;
        } else {
            Member borrower = borrowedBy;
            borrowedBy = null;
            return borrower;
        }
    }

    /**
     * Renews the item
     *
     * @param member who wants to renew the item
     * @return true if the item could be renewed
     */
    public boolean renew(Member member) {
        if (hasHold()) {
            return false;
        }
        if ((member.getId()).equals(borrowedBy.getId())) {
            return (issue(member));
        }
        return false;
    }

    /**
     * Adds one more hold to the book
     *
     * @param hold the new hold on the book
     */
    public void placeHold(Hold hold) {
        holds.add(hold);
    }

    /**
     * Removes hold for a specific member
     *
     * @param memberId whose hold has to be removed
     * @return true iff the hold could be removed
     */
    public boolean removeHold(String memberId) {
        for (ListIterator iterator = holds.listIterator(); iterator.hasNext(); ) {
            Hold hold = (Hold) iterator.next();
            String id = hold.getMember().getId();
            if (id.equals(memberId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a valid hold
     *
     * @return the next valid hold
     */
    public Hold getNextHold() {
        for (ListIterator iterator = holds.listIterator(); iterator.hasNext(); ) {
            Hold hold = (Hold) iterator.next();
            iterator.remove();
            if (hold.isValid()) {
                return hold;
            }
        }
        return null;
    }

    /**
     * Checks whether there is a hold on this book
     *
     * @return true iff there is a hold
     */
    public boolean hasHold() {
        ListIterator iterator = holds.listIterator();
        if (iterator.hasNext()) {
            return true;
        }
        return false;
    }

    /**
     * Returns an iterator for the holds
     *
     * @return iterator for the holds on the book
     */
    public Iterator getHolds() {
        return holds.iterator();
    }

    /**
     * Getter for id
     *
     * @return id of the book
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for borrower
     *
     * @return the member who borrowed the book
     */
    public Member getBorrower() {
        return borrowedBy;
    }

    /**
     * Getter for due date
     *
     * @return the date on which the book is due
     */
    public String getDueDate() {
        return (dueDate.getTime().toString());
    }

    /**
     * String form of the book
     */
    public String toString() {
        return String.format("ID: %s; Borrowed by: %s;", getId(), getBorrower());
    }
}
