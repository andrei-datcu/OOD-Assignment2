package items.models;

/**
 * Created by vlad on 10/04/16.
 */
public class CountingVisitor implements ItemVisitor {
    private int bookCount;
    private int cdCount;
    private int dvdCount;
    private int laptopCount;
    private int cameraCount;

    @Override
    public void visit(Book book) {
        bookCount++;
    }

    @Override
    public void visit(CD cd) {
        cdCount++;
    }

    @Override
    public void visit(DVD dvd) {
        dvdCount++;
    }

    @Override
    public void visit(Laptop laptop) {
        laptopCount++;
    }

    @Override
    public void visit(Camera camera) {
        cameraCount++;
    }

    @Override
    public void visit(Periodical periodical) {

    }

    @Override
    public String toString() {
        String rtn = "Books: " + bookCount;
        rtn = rtn + " CDs: " + cdCount;
        rtn = rtn + " DVDs: " + dvdCount;
        rtn = rtn + " Laptops: " + laptopCount;
        rtn = rtn + " Cameras: " + cameraCount;

        return rtn;
    }
}
