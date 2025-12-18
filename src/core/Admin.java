
package core;

import java.util.ArrayList;
import utils.FileManager;
import utils.Query;

public class Admin extends User {
    private boolean superAdmin;
    
    public Admin(String id, String name, String email, String password, boolean isSuperAdmin) {
        super(id, name, email, password);
        this.superAdmin = isSuperAdmin;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setIsSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    
    
    public static Admin findById(String id){
        Query q = new Query(FileManager.FILES.USERS);
        q.addQuery("id", id);
        String row = FileManager.findSingleRow(q);
        if (row.length() <= 0) return null;
        return parseAdmin(row);   
    }
    
    public static ArrayList<Admin> findAll(Query q) {
        q.defineQueryRules(FileManager.FILES.USERS);
        String[] rows = FileManager.findRows(q);
        ArrayList<Admin> users = new ArrayList<>();
        for (String row : rows) {
            users.add(parseAdmin(row));
        }

        return users;
    }
    
    public static Admin parseAdmin(String row) {
        String[] parts = row.split("\\|");
        Admin u = new Admin(parts[0], parts[1], 
                parts[2], parts[4], 
                parts[3] == "true");

        return u;
    }

    public void save() {
        String row = this.getId() + "|" 
                   + this.getName() + "|" 
                   + this.getEmail() + "|" 
                   + this.isSuperAdmin() + "|" 
                   + this.getPassword();

        Query q = new Query(FileManager.FILES.USERS);
        q.addRowToInsert(row);

        FileManager.insertRow(q);
    }
 
}