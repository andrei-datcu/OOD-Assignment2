package items.models;

/**
 * Created by vlad on 10/04/16.
 */
public interface ItemVisitor {
    void visit(Book book);
    void visit(CD cd);
    void visit(DVD dvd);
    void visit(Laptop laptop);
    void visit(Camera camera);
    void visit(Periodical periodical);
}
