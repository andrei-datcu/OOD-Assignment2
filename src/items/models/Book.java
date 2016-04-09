package items.models;

/**
 * Book interface implemented by all book classes (including decorated ones)
 */
public interface Book extends Item {
    String getAuthor();
    String getTitle();
}
