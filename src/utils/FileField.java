package utils;

public class FileField {
    private String id;
    private String type;
    private boolean index;
    private boolean unique;
    private int posIndex;

    public FileField(String id, String type, boolean index, boolean unique, int posIndex) {
        this.id = id;
        this.type = type;
        this.index = index;
        this.unique = unique;
        this.posIndex = posIndex;
    }

    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public boolean isIndex() {
        return index;
    }
    public boolean isUnique() {
        return unique;
    }

    public int getPosIndex() {
        return posIndex;
    }
    
    
    @Override
    public String toString() {
        return "FileField{" + "id=" + id + ", type=" + type + ", index=" + index + ", unique=" + unique + '}';
    }
}
