package items.models;

/**
 * Class that encapsulates all lonable items that have an author and a title
 */
public abstract class MediaItem extends BaseItem {
    private String title;
    private String author;

    /**
     * Creates an item with the given id, title, and author name
     *
     * @param title  item title
     * @param author author name
     * @param id     item id
     */
    public MediaItem(String title, String author, String id) {
        super(id);
        this.title = title;
        this.author = author;
    }

    /**
     * Getter for author
     *
     * @return author name
     */
    public String getAuthor() {
        return author;
    }

    /**
     * getter for title
     *
     * @return title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * String form of the book
     */
    public String toString() {
        return String.format("%s; Author: %s; Title: %s;", super.toString(), getAuthor(), getTitle());
    }
}
