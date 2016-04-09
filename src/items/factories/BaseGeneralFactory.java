package items.factories;

import items.models.Item;

/**
 * Factory that automatically generates an item, based on its class and constructor signature
 */
abstract class BaseGeneralFactory extends BaseFactory {
    private Class<? extends Item> generatedClass;

    BaseGeneralFactory(Class<? extends Item> cls) {
        generatedClass = cls;
    }

    /**
     * Try generate a loanable item. If the arguments are not right, null is returned
     * @param arguments passed to the Item's constructor
     * @return generated item, or null if arguments are not good
     */
    public Item generate(Object[] arguments) {
        Class[] constructorSignature = getConstructorSignature();
        if (arguments.length != constructorSignature.length) {
                return null;
        }

        for (int i = 0; i < arguments.length; ++i) {
            if (!constructorSignature[i].isInstance(arguments[i])) {
                return null;
            }
        }

        try {
            return generatedClass.getConstructor(constructorSignature).newInstance(arguments);
        } catch (Exception e) {
            return null;
        }
    }

    protected abstract Class[] getConstructorSignature();
}
