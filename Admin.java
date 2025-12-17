
package PLProject;

import java.util.ArrayList;

/**
 *
 * @author FatmaAli
 */
public class Admin extends Users{
    private boolean isSuperAdmin;
    public Admin(boolean isSuperAdmin, String name, String email, String password) throws PasswordException{
        super(name, email, password);
        if (password.length() < 6) {
            throw  new PasswordException("Password must at least contain 6 characters.");        
        }
        this.isSuperAdmin = isSuperAdmin;
    }

    public boolean isIsSuperAdmin() {
        return isSuperAdmin;
    }


    public UID getId() {
        return id;
    }

   @Override
public void save() {
    String row = id.toString() + "|" 
               + name + "|" 
               + email + "|" 
               + password + "|" 
               + isSuperAdmin;

    Query q = new Query(FileManager.FILES.USERS);
    q.addRowToInsert(row);

    FileManager.insertRow(q.getFilePath(), row);
}

public Employee createEmployee(
        String name,
        String email,
        String password,
        String type
) throws PasswordException {

    Employee e = new Employee(type, this, name, email, password);
    e.save();
    return e;
}
public ArrayList<Users> getCreatedEmployees() {
    Query q = new Query(FileManager.FILES.EMPLOYEES);
    q.addQuery("creatorId", this.id.toString());

    return Users.findAll(q);
}

public void deleteEmployee(String employeeId) {
    if (!isSuperAdmin) {
        System.out.println("Only super admins can delete employees.");
        return;
    }

    Query q = new Query(FileManager.FILES.EMPLOYEES);
    q.addQuery("id", employeeId);

    FileManager.deleteRow(q);
}
public void updateEmployee(String employeeId, String field, String newValue) {
    Query q = new Query(FileManager.FILES.EMPLOYEES);
    q.addQuery("id", employeeId);
    q.addUpdateClause(field, newValue);

    FileManager.updateRows(q);
}

    
}
