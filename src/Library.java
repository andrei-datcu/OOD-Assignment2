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
import items.factories.MainFactory;
import items.models.CountingVisitor;
import items.models.Item;
import items.Catalog;
import items.Hold;
import member.Member;
import member.MemberIdServer;
import member.MemberList;

import java.util.*;
import java.io.*;
public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int ITEM_NOT_FOUND  = 1;
    public static final int ITEM_NOT_ISSUED  = 2;
    public static final int ITEM_HAS_HOLD  = 3;
    public static final int ITEM_ISSUED  = 4;
    public static final int HOLD_PLACED  = 5;
    public static final int NO_HOLD_FOUND  = 6;
    public static final int OPERATION_COMPLETED= 7;
    public static final int OPERATION_FAILED= 8;
    public static final int NO_SUCH_MEMBER = 9;
    private Catalog catalog;
    private MemberList memberList;
    private static Library library;
    /**
     * Private for the singleton pattern
     * Creates the catalog and member collection objects
     */
    private Library() {
        catalog = Catalog.instance();
        memberList = MemberList.instance();
    }
    /**
     * Supports the singleton pattern
     *
     * @return the singleton object
     */
    public static Library instance() {
        if (library == null) {
            MemberIdServer.instance(); // instantiate all singletons
            return (library = new Library());
        } else {
            return library;
        }
    }
    /**
     * Organizes the operations for adding an item
     * @param type item type
     * @param arguments general arguments used for item creation
     * @return the Item object created
     */
    public Item addItem(String type, Object[] arguments) {
        Item item = MainFactory.generateItem(type, arguments);
        if (catalog.insertItem(item)) {
            return item;
        }
        return null;
    }
    /**
     * Organizes the operations for adding a member
     * @param name member name
     * @param address member address
     * @param phone member phone
     * @return the member.Member object created
     */
    public Member addMember(String name, String address, String phone) {
        Member member = new Member(name, address, phone);
        if (memberList.insertMember(member)) {
            return (member);
        }
        return null;
    }
    /**
     * Organizes the placing of a hold
     * @param memberId member's id
     * @param itemId item's id
     * @param duration for how long the hold should be valid in days
     * @return indication on the outcome
     */
    public int placeHold(String memberId, String itemId, int duration) {
        Item item = catalog.search(itemId);
        if (item == null) {
            return(ITEM_NOT_FOUND);
        }
        if (item.getBorrower() == null) {
            return(ITEM_NOT_ISSUED);
        }
        Member member = memberList.search(memberId);
        if (member == null) {
            return(NO_SUCH_MEMBER);
        }
        Hold hold = new Hold(member, item, duration);
        item.placeHold(hold);
        member.placeHold(hold);
        return(HOLD_PLACED);
    }
    /**
     * Searches for a given member
     * @param memberId id of the member
     * @return true iff the member is in the member list collection
     */
    public Member searchMembership(String memberId) {
        return memberList.search(memberId);
    }
    /**
     * Processes holds for a single item
     * @param itemId id of the item
     * @return the member who should be notified
     */
    public Member processHold(String itemId) {
        Item item = catalog.search(itemId);
        if (item == null) {
            return (null);
        }
        Hold hold = item.getNextHold();
        if (hold == null) {
            return (null);
        }
        hold.getMember().removeHold(itemId);
        hold.getItem().removeHold(hold.getMember().getId());
        return (hold.getMember());
    }
    /**
     * Removes a hold for a specific item and member combincation
     * @param memberId id of the member
     * @param itemId item id
     * @return result of the operation
     */
    public int removeHold(String memberId, String itemId) {
        Member member = memberList.search(memberId);
        if (member == null) {
            return (NO_SUCH_MEMBER);
        }
        Item item = catalog.search(itemId);
        if (item == null) {
            return(ITEM_NOT_FOUND);
        }
        return member.removeHold(itemId) && item.removeHold(memberId)? OPERATION_COMPLETED: NO_HOLD_FOUND;
    }
    /*
     * Removes all out-of-date holds
     */
    private void removeInvalidHolds() {
        for (Iterator catalogIterator = catalog.getItems(); catalogIterator.hasNext(); ) {
            for (Iterator iterator = ((Item) catalogIterator.next()).getHolds(); iterator.hasNext(); ) {
                Hold hold = (Hold) iterator.next();
                if (!hold.isValid()) {
                    hold.getItem().removeHold(hold.getMember().getId());
                    hold.getMember().removeHold(hold.getItem().getId());
                }
            }
        }
    }
    /**
     * Organizes the issuing of a item
     * @param memberId member id
     * @param itemId item id
     * @return the item issued
     */
    public Item issueItem(String memberId, String itemId) {
        Item item = catalog.search(itemId);
        if (item == null) {
            return(null);
        }
        if (item.getBorrower() != null) {
            return(null);
        }
        Member member = memberList.search(memberId);
        if (member == null) {
            return(null);
        }
        if (!(item.issue(member) && member.issue(item))) {
            return null;
        }
        return(item);
    }
    /**
     * Renews a item
     * @param itemId id of the item to be renewed
     * @param memberId member id
     * @return the item renewed
     */
    public Item renewItem(String itemId, String memberId) {
        Item item = catalog.search(itemId);
        if (item == null) {
            return(null);
        }
        Member member = memberList.search(memberId);
        if (member == null) {
            return(null);
        }
        if ((item.renew(member) && member.renew(item))) {
            return(item);
        }
        return(null);
    }
    /**
     * Returns an iterator to the items issued to a member
     * @param memberId member id
     * @return iterator to the collection
     */
    public Iterator getItems(String memberId) {
        Member member = memberList.search(memberId);
        if (member == null) {
            return(null);
        } else {
            return (member.getItemsIssued());
        }
    }
    /**
     * Removes a specific item from the catalog
     * @param itemId id of the item
     * @return a code representing the outcome
     */
    public int removeItem(String itemId) {
        Item item = catalog.search(itemId);
        if (item == null) {
            return(ITEM_NOT_FOUND);
        }
        if (item.hasHold()) {
            return(ITEM_HAS_HOLD);
        }
        if ( item.getBorrower() != null) {
            return(ITEM_ISSUED);
        }
        if (catalog.removeItem(itemId)) {
            return (OPERATION_COMPLETED);
        }
        return (OPERATION_FAILED);
    }
    /**
     * Returns a single item
     * @param itemId id of the item to be returned
     * @return a code representing the outcome
     */
    public int returnItem(String itemId) {
        Item item = catalog.search(itemId);
        if (item == null) {
            return(ITEM_NOT_FOUND);
        }
        Member member = item.returnItem();
        if (member == null) {
            return(ITEM_NOT_ISSUED);
        }
        if (!(member.returnItem(item))) {
            return(OPERATION_FAILED);
        }

        // Apply fine to member according to the item compute rules
        member.applyFine(item.computeFine());

        if (item.hasHold()) {
            return(ITEM_HAS_HOLD);
        }
        return(OPERATION_COMPLETED);
    }

    /**
     * Adds amount to the fine owed in bani by the member with memberId
     * @param memberId member id
     * @param amount amount (negative or positive)
     * @return a code representing the outcome
     */
    public int addFines(String memberId, int amount) {
        Member member = library.searchMembership(memberId);
        if (member == null) {
            return (NO_SUCH_MEMBER);
        }

        member.applyFine(amount);

        return(OPERATION_COMPLETED);
    }

    /**
     * Returns a count of the items in the catalog
     * @return object holding the count of each items category
     */
    public CountingVisitor countItems() {
        CountingVisitor countingVisitor = new CountingVisitor();
        catalog.visitItems(countingVisitor);

        return countingVisitor;
    }

    /**
     * Returns an iterator to the transactions for a specific member on a certain date
     * @param memberId member id
     * @param date date of issue
     * @return iterator to the collection
     */
    public Iterator getTransactions(String memberId, Calendar date) {
        Member member = memberList.search(memberId);
        if (member == null) {
            return(null);
        }
        return member.getTransactions(date);
    }
    /**
     * Retrieves a deserialized version of the library from disk
     * @return a Library object
     */
    public static Library retrieve() {
        try {
            FileInputStream file = new FileInputStream("LibraryData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            MemberIdServer.retrieve(input);
            return library;
        } catch(IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }
    /**
     * Serializes the Library object
     * @return true iff the data could be saved
     */
    public static  boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("LibraryData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(library);
            output.writeObject(MemberIdServer.instance());
            return true;
        } catch(IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }
    /**
     * Writes the object to the output stream
     * @param output the stream to be written to
     */
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(library);
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }
    /**
     * Reads the object from a given stream
     * @param input the stream to be read
     */
    private void readObject(java.io.ObjectInputStream input) {
        try {
            input.defaultReadObject();
            if (library == null) {
                library = (Library) input.readObject();
            } else {
                input.readObject();
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /** String form of the library
     *
     */
    @Override
    public String toString() {
        return catalog + "\n" + memberList;
    }
}