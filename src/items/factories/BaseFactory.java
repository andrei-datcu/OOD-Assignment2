package items.factories;

import items.models.Item;

/**
 * Base factory class to generate a loanable item from variable arguments
 */
public abstract class BaseFactory {
    public abstract Item generate(Object[] arguments);
}
