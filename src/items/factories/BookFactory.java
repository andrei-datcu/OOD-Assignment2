package items.factories;

import items.models.CostlyBookDecorator;
import items.models.Item;
import items.models.NewBookDecorator;
import items.models.SimpleBook;

/**
 * Factory for generating media items
 */
public class BookFactory extends BaseFactory {

    @Override
    public Item generate(Object[] arguments) {

        if (arguments.length != 4) {
            return null;
        }

        try {
            String title = (String) arguments[0];
            String author = (String) arguments[1];
            String id = (String) arguments[2];
            Boolean costly = (Boolean) arguments[3];

            SimpleBook book = new SimpleBook(title, author, id);
            return new NewBookDecorator(costly ? new CostlyBookDecorator(book) : book);
        } catch (ClassCastException e) {
            return null;
        }
    }
}
