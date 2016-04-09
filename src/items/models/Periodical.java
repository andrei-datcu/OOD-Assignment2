package items.models;

import member.Member;
import java.util.Calendar;

/**
 * Class for all periodical items
 */
public class Periodical extends BaseItem {

    private String title;

    /**
     * Creates an item with the given id
     *
     * @param title periodical's title
     * @param id item id
     */
    public Periodical(String title, String id) {
        super(id);
        this.title = title;
    }

    @Override
    public boolean issue(Member member) {
        Calendar now = Calendar.getInstance();
        Calendar availableDate = getAddedDate();
        availableDate.add(Calendar.DATE, 90);

        if (now.before(availableDate)) {
            return false;
        } else {
            return super.issue(member);
        }
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("%s title: %s;", super.toString(), getTitle());
    }
}
