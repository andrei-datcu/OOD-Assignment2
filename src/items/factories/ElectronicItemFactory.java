package items.factories;

import items.models.ElectronicItem;

/**
 * Factory for generating electronic items
 */
public class ElectronicItemFactory extends BaseGeneralFactory {

    public ElectronicItemFactory(Class<? extends ElectronicItem> cls) {
        super(cls);
    }

    @Override
    protected Class[] getConstructorSignature() {
        return new Class[] {String.class, String.class, String.class};
    }
}
