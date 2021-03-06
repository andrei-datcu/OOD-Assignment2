package items.factories;

import items.models.ElectronicItem;

/**
 * Factory for generating electronic items
 */
class ElectronicItemFactory extends BaseGeneralFactory {

    ElectronicItemFactory(Class<? extends ElectronicItem> cls) {
        super(cls);
    }

    @Override
    protected Class[] getConstructorSignature() {
        return new Class[] {String.class, String.class, String.class};
    }

    @Override
    public String[] getParameterNames() {
        return new String[] {"brand", "model", "id"};
    }
}
