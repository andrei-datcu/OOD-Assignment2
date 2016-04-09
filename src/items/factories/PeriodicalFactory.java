package items.factories;

import items.models.Item;
import items.models.Periodical;

class PeriodicalFactory extends BaseGeneralFactory {

    PeriodicalFactory() {
        super(Periodical.class);
    }

    @Override
    protected Class[] getConstructorSignature() {
        return new Class[]{String.class, String.class};
    }

    @Override
    public String[] getParameterNames() {
        return new String[]{"title", "id"};
    }
}
