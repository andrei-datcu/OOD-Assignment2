package items.models;

/**
 * Created by andrei on 4/9/16.
 */
public class Camera extends ElectronicItem {
    /**
     * Creates an item with the given id
     *
     * @param brand device's brand (such as Apple. Asus etc.)
     * @param model device's model (such MacBook, ZenBook etc.)
     * @param id    item id
     */
    public Camera(String brand, String model, String id) {
        super(brand, model, id);
    }

    /**
     * Accept method for the visitor pattern
     *
     * @param visitor
     */
    @Override
    public void accept(ItemVisitor visitor) {
        visitor.visit(this);
    }
}
