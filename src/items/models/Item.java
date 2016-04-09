package items.models;

import items.Hold;
import member.Member;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Loanable item. Every lonable item that is added in the Catalog
 * must implement this
 */
public interface Item extends Serializable {
    /**
     * Marks the item as issued to a member
     *
     * @param member the borrower
     * @return true if the item could be issued.
     */
    boolean issue(Member member);

    /**
     * Marks the item as returned
     *
     * @return The member who had borrowed the item
     */
    Member returnItem();

    /**
     * Renews the item
     *
     * @param member who wants to renew the item
     * @return true if the item could be renewed
     */
    boolean renew(Member member);

    /**
     * Adds one more hold to the item
     *
     * @param hold the new hold on the item
     */
    void placeHold(Hold hold);

    /**
     * Removes hold for a specific member
     *
     * @param memberId whose hold has to be removed
     * @return true iff the hold could be removed
     */
    boolean removeHold(String memberId);

    /**
     * @return the next valid hold
     */
    Hold getNextHold();

    /**
     * @return true iff there is a hold
     */
    boolean hasHold();

    /**
     * @return iterator for the holds on the item
     */
    Iterator getHolds();

    /**
     * @return id of the item
     */
    String getId();

    /**
     * @return the member who borrowed the item
     */
    Member getBorrower();

    /**
     * @return the date on which the item is due
     */
    String getDueDate();

    /**
     * @return the date on which the item was added
     */
    Calendar getAddedDate();

    /**
     * String form of the item
     */
    String toString();
}
