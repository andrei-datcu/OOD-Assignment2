package items.factories;

import items.models.Item;
import items.models.OpticalMediaItem;

/**
 * Factory for Optiocal Media Items
 */
public class OpticalMediaItemFactory extends BaseGeneralFactory {

    OpticalMediaItemFactory(Class<? extends OpticalMediaItem> cls) {
        super(cls);
    }

    @Override
    public Item generate(Object[] arguments) {
        if (arguments.length != 4) {
            return null;
        }

        // Support string for number as well
        Object duration = arguments[2];
        if (!(duration instanceof Integer)) {
            if (duration instanceof String) {
                arguments[2] = Integer.parseInt((String) duration);
            } else {
                return null;
            }
        }
        return super.generate(arguments);
    }

    @Override
    protected Class[] getConstructorSignature() {
        return new Class[] {String.class, String.class, Integer.class, String.class};
    }
}
