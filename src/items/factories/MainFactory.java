package items.factories;

import items.models.*;

import java.util.HashMap;

/**
 * Factory used to instantiate an item by its type name and a series of general arguments
 */
public class MainFactory {
    final private static HashMap<String, BaseFactory> knownFactories = new HashMap<>();
    static {
        knownFactories.put("cd", new OpticalMediaItemFactory(CD.class));
        knownFactories.put("dvd", new OpticalMediaItemFactory(DVD.class));
        knownFactories.put("camera", new ElectronicItemFactory(Camera.class));
        knownFactories.put("laptop", new ElectronicItemFactory(Laptop.class));
        knownFactories.put("book", new BookFactory());
        knownFactories.put("periodical", new PeriodicalFactory());
    }

    /**
     * @return array of supported item types
     */
    public static String[] getSupportedTypes() {
        return knownFactories.keySet().toArray(new String[0]);
    }

    /**
     * Generate a new loanable item
     * @param type item type (one of getSupportedTypes())
     * @param arguments general arguments used to construct the item
     * @return the item or null if the type is wrong, or the arguments are bad
     */
    public static Item generateItem(String type, Object[] arguments) {
        BaseFactory f = knownFactories.get(type);
        if (f == null) {
            return null;
        }
        return f.generate(arguments);
    }
}
