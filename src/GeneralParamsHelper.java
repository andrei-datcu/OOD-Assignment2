import items.factories.MainFactory;

/**
 * Helper for printing and retrieving items's parameter names
 */

public class GeneralParamsHelper {
    static String[] getParameterNames(String type) {
        return MainFactory.getParameterNames(type);
    };

    static String getSupportedItemTypes() {
        String[] types = MainFactory.getSupportedTypes();
        StringBuilder sb = new StringBuilder();

        for (String type : types) {
            sb.append(type);
            sb.append(" | ");
        }
        sb.delete(sb.length() - 2, sb.length());

        return sb.toString();
    }
}
