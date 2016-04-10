package items.models;

/**
 * Base class for all electronic items (laptops, cameras etc.)
 */
public abstract class ElectronicItem extends BaseItem {

    private String brand;
    private String model;

    /**
     * Creates an item with the given id
     *
     * @param brand device's brand (such as Apple. Asus etc.)
     * @param model device's model (such MacBook, ZenBook etc.)
     * @param id item id
     */
    public ElectronicItem(String brand, String model, String id) {
        super(id);
        this.brand = brand;
        this.model = model;
    }

    /**
     * @return electronic device's brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     *
     * @return electronic device's model
     */
    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return String.format("%s brand: %s; model: %s", super.toString(), getBrand(), getModel());
    }
}
