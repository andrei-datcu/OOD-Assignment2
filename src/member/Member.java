package member;
/**
 *
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010

 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS"AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  
 */
import items.Hold;
import items.Transaction;
import items.models.Item;

import java.util.*;
import java.io.*;
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String phone;
    private String id;
    private static final String MEMBER_STRING = "M";
    private List itemsBorrowed = new LinkedList();
    private List itemsOnHold = new LinkedList();
    private List transactions = new LinkedList();
    private int fineOwed = 0;
    /**
     * Represents a single member
     * @param name name of the member
     * @param address address of the member
     * @param phone phone number of the member
     */
    public  Member (String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        id = MEMBER_STRING + (MemberIdServer.instance()).getId();
    }
    /**
     * Stores the item as issued to the member
     * @param item the item to be issued
     * @return true iff the item could be marked as issued. always true currently
     */
    public boolean issue(Item item) {
        if (itemsBorrowed.add(item)) {
            transactions.add(new Transaction("Item issued ", item.toString()));
            return true;
        }
        return false;
    }
    /**
     * Marks the item as not issued to the member
     * @param item the item to be returned
     * @return true iff the item could be marked as marked as returned
     */
    public boolean returnItem(Item item) {
        if ( itemsBorrowed.remove(item)){
            transactions.add(new Transaction("Item returned ", item.toString()));
            return true;
        }
        return false;
    }
    /**
     * Marks the item as renewe
     * @param item the item to be renewed
     * @return true iff the item could be renewed
     */
    public boolean renew(Item item) {
        for (ListIterator iterator = itemsBorrowed.listIterator(); iterator.hasNext(); ) {
            Item anItem = (Item) iterator.next();
            String id = anItem.getId();
            if (id.equals(item.getId())) {
                transactions.add(new Transaction("Item renewed ",  item.toString()));
                return true;
            }
        }
        return false;
    }
    /**
     * Gets an iterator to the issued items
     * @return Iterator to the collection of issued books
     */
    public Iterator getItemsIssued() {
        return (itemsBorrowed.listIterator());
    }
    /**
     * Places a hold for the book
     * @param hold the book to be placed a hold
     */
    public void placeHold(Hold hold) {
        transactions.add(new Transaction("Hold Placed ", hold.getItem().toString()));
        itemsOnHold.add(hold);
    }
    /**
     * Removes a hold
     * @param bookId the book id for removing a hold
     * @return true iff the hold could be removed
     */
    public boolean removeHold(String bookId) {
        for (ListIterator iterator = itemsOnHold.listIterator(); iterator.hasNext(); ) {
            Hold hold = (Hold) iterator.next();
            String id = hold.getItem().getId();
            if (id.equals(bookId)) {
                transactions.add(new Transaction("items.Hold Removed ", hold.getItem().toString()));
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    /**
     * Gets an iterator to a collection of selected ransactions
     * @param date the date for which the transactions have to be retrieved
     * @return the iterator to the collection
     */
    public Iterator getTransactions(Calendar date) {
        List result = new LinkedList();
        for (Iterator iterator = transactions.iterator(); iterator.hasNext(); ) {
            Transaction transaction = (Transaction) iterator.next();
            if (transaction.onDate(date)) {
                result.add(transaction);
            }
        }
        return (result.iterator());
    }
    /**
     * Getter for name
     * @return member name
     */
    public String getName() {
        return name;
    }
    /**
     * Getter for phone number
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Getter for address
     * @return member address
     */
    public String getAddress() {
        return address;
    }
    /**
     * Getter for id
     * @return member id
     */
    public String getId() {
        return id;
    }
    /**
     * Setter for name
     * @param newName member's new name
     */
    public void setName(String newName) {
        name = newName;
    }
    /**
     * Setter for address
     * @param newAddress member's new address
     */
    public void setAddress(String newAddress) {
        address = newAddress;
    }
    /**
     * Setter for phone
     * @param newPhone member's new phone
     */
    public void setPhone(String newPhone) {
        phone = newPhone;
    }
    /**
     * Adds amount to the fine owed by the current member
     * @param amount of bani to fine the member. Can also be negative.
     */
    public void applyFine(int amount) {
        fineOwed += amount;
    }
    /**
     * The amount of bani owed in fines
     * @return total fine owed
     */
    public int getFineOwed() {
        return fineOwed;
    }
    /**
     * Checks whether the member is equal to the one with the given id
     * @param id of the member who should be compared
     * @return true iff the member ids match
     */
    public boolean equals(String id) {
        return this.id.equals(id);
    }
    /**
     * String form of the member
     *
     */
    @Override
    public String toString() {
        String string = "Member name " + name + " address " + address + " id " + id + "phone " + phone;
        string += " borrowed: [";
        for (Iterator iterator = itemsBorrowed.iterator(); iterator.hasNext(); ) {
            Item item = (Item) iterator.next();
            string += " " + item.toString();
        }
        string += "] holds: [";
        for (Iterator iterator = itemsOnHold.iterator(); iterator.hasNext(); ) {
            Hold hold = (Hold) iterator.next();
            string += " " + hold.getItem().toString();
        }
        string += "] transactions: [";
        for (Iterator iterator = transactions.iterator(); iterator.hasNext(); ) {
            string += (Transaction) iterator.next();
        }
        string += "] fines Owed: " + fineOwed;
        return string;
    }
}