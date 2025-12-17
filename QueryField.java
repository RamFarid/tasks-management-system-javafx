package PLProject;

public class QueryField {
    public static enum OPERATORS {
        EQUALS,
        CONTAINS,
        GREATER_THAN_OR_EQUAL,
        GREATER_THAN,
        SMALLER_THAN,
        SMALLER_THAN_OR_EQUAL,
        NOT_EQUAL,
        NOT_CONTAINS
    }
    String key;
    String value;
    OPERATORS operator;
    
    public QueryField(String key, OPERATORS operator, String value) {
        this.key = key;
        this.value = value;
        this.operator = operator;
    }
    public QueryField(String key, String value) {
        this.key = key;
        this.value = value;
        this.operator = OPERATORS.EQUALS;
    }
    public static boolean isSafeField(String data) {
        return !data.contains("|");
    }
    public boolean isMatchedField(String field) {
        switch (this.operator) {
            case QueryField.OPERATORS.EQUALS:
                return field.equals(this.value);
            case QueryField.OPERATORS.CONTAINS:
                return field.toLowerCase().contains(this.value.toLowerCase());
            case QueryField.OPERATORS.GREATER_THAN:
                return Integer.parseInt(this.value) > Integer.parseInt(field);
            case QueryField.OPERATORS.GREATER_THAN_OR_EQUAL:
                return Integer.parseInt(this.value) >= Integer.parseInt(field);
            case QueryField.OPERATORS.SMALLER_THAN:
                return Integer.parseInt(this.value) < Integer.parseInt(field);
            case QueryField.OPERATORS.SMALLER_THAN_OR_EQUAL:
                return Integer.parseInt(this.value) <= Integer.parseInt(field);
            case QueryField.OPERATORS.NOT_EQUAL:
                return !(field.equals(this.value));
            case QueryField.OPERATORS.NOT_CONTAINS:
                return !(field.toLowerCase().contains(this.value.toLowerCase()));
            default:
                return field.equals(this.value);
        }
    }
    
    @Override
    public String toString() {
        return "{ key: " + key + ", value: " + value + " }";
    }
}
