package items.models;

/**
 * Created by andrei on 4/9/16.
 */
public abstract class OpticalMediaItem extends MediaItem {

    private Integer duration;

    /**
     * Creates an item with the given id, title, and author name
     *
     * @param title  item title
     * @param author author name
     * @param duration Content's duration in seconds
     * @param id     item id
     */
    public OpticalMediaItem(String title, String author, Integer duration, String id) {
        super(title, author, id);
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }
}
