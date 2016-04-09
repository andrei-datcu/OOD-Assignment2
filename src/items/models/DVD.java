package items.models;

/**
 * Created by andrei on 4/9/16.
 */
public class DVD extends OpticalMediaItem {
    /**
     * Creates an item with the given id, title, and author name
     *
     * @param title    item title
     * @param author   author name
     * @param duration Content's duration in seconds
     * @param id       item id
     */
    public DVD(String title, String author, Integer duration, String id) {
        super(title, author, duration, id);
    }
}
