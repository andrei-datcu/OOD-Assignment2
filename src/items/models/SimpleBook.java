package items.models;
/**
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * - the use is for academic purpose only
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * - Neither the name of Brahma Dathan or Sarnath Ramnath
 * may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS"AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.
 */

import java.lang.*;

/**
 * Represents a single book
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class SimpleBook extends MediaItem implements Book {

    /**
     * Creates a book with the given id, title, and author name
     * @param title book title
     * @param author author name
     * @param id book id
     */
    public SimpleBook(String title, String author, String id) {
        super(title, author, id);
    }

    /**
     * @return the fine that is owed for this item;
     */
    @Override
    public int computeFine() {
        int dueDays = DateUtils.daysBetween(getDueDate().getTimeInMillis(),
                System.currentTimeMillis());

        if (dueDays == 0)
            return 0;

        return 15 + 5 * (dueDays - 1);
    }

    /**
     * Accept method for the visitor pattern
     *
     * @param visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit((Book)this);
    }
}