package utils;
import java.util.HashMap;

public class Query {
    private HashMap<String, QueryField> queryFields = new HashMap<>(); // Finding queries
    private String operator = "AND"; // How does queries in `queryFields` 
    // deals with each others?
    // AND => Returned Row should Match all queries together
    // OR => Returned Row could be matched by one query only
    private FileManager.FILES queryRules; // It defines rules about 
    // the file you need to insert in, You can found rules in
    // FileManager.Files.*
    private HashMap<String, UpdateClause> updateClauses = new HashMap<>();
    // It is a map of key-value UpdateClause object contains fields need
    // to update
    private int limit = -1;
    private String sortyBy = "";
    private String rowToInsert = "";
    
    public Query() {}
    public Query(QueryField[] fields) {
        for (QueryField field : fields) {
            this.queryFields.put(field.key, field);
        }
    }
    public Query(QueryField[] fields, FileManager.FILES rules) {
        for (QueryField field : fields) {
            this.queryFields.put(field.key, field);
        }
        this.defineQueryRules(rules);
    }
    public Query(FileManager.FILES rules) {
        this.defineQueryRules(rules);
    }
    public void setLimit(int limit) {
        if(limit > 0) this.limit = limit;
    }
    public void sortBy(String field) {
        this.sortyBy = field;
    }
    public String getFilePath() {
        return this.queryRules.filePath;
    }
    public void defineQueryRules(FileManager.FILES queryRules) {
        this.queryRules = queryRules;
    }
    public void defineQueryOperator(String operator) {
        this.operator = operator;
    }
    public void addQuery(String key, String value) {
        queryFields.put(key, new QueryField(key, value));
    }
    public void addQuery(String key, QueryField.OPERATORS op, String value) {
        queryFields.put(key, new QueryField(key, op, value));
    }
    public void addQuery(QueryField qf) {
        queryFields.put(qf.key, qf);
    }
    public void addUpdateClause(UpdateClause uc) {
        updateClauses.put(uc.key, uc);
    }
    public void addUpdateClause(String key, String value) {
        updateClauses.put(key, new UpdateClause(key, value));
    }
    public boolean isMatchedQuery(String row) {
        String[] fieldValues = row.split("\\|");
        
        int matched = 0;
        int totalConditions = queryFields.size();

        for (int i = 0; i < queryRules.fieldIDs.length; i++) {
            String fieldName = queryRules.fieldIDs[i];
            String fieldValue = fieldValues[i].trim();

            if (queryFields.containsKey(fieldName)) {
                QueryField expectedField = queryFields.get(fieldName);
                if(expectedField.isMatchedField(fieldValue)) matched++;
            }
        }
        
        if (operator.toUpperCase().equalsIgnoreCase("OR")) {
            return matched > 0;
        } else {
            return matched == totalConditions;
        }
    }
    public String updateRow(String row) {
        String[] splitted = row.split("\\|");
        for (int index = 0; index < queryRules.fieldIDs.length; index++) {
            String fieldName = queryRules.fieldIDs[index];
            if(updateClauses.containsKey(fieldName)) {
                UpdateClause uc = updateClauses.get(fieldName);
                splitted[index] = uc.value;
            }
        }
        return String.join("|", splitted);
    }
    public String addRowToInsert(String row) {
        return this.rowToInsert = row;
    }
    public String getRowToInsert() {
        return this.rowToInsert;
    }
    @Override
    public String toString() {
        return "Queries: " + this.queryFields.toString()
                + "\nUpdateClauses: " + this.updateClauses.toString();
    }
}
