package PLProject;

import java.util.ArrayList;

/**
 *
 * @author FatmaAli
 */
public class Users {
UID id; 
String name;
String email;
String password;

    public Users(String name, String email, String password) throws PasswordException{
        this.id = new UID();
        this.name = name;
        this.email = email;
        this.password = password;
        if (password.length() < 6)
            throw new PasswordException("Password must at least contain 6 characters.");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

//    public boolean comparePassword(String password){
//        return password.equals(this.password);
//    } Original. more simple.
    public void ComparePassword (String pswd) throws PasswordException{
      if (!pswd.equals(password))
          throw new PasswordException("Incorrect Password.");
    } //better gives an error message

    public void changePassword(String newPassword) throws PasswordException{
        if ( newPassword == null || newPassword.length() < 6)
            throw new PasswordException("Password must at least contain 6 characters.");
        else if (newPassword.equals(password))
            throw new PasswordException("Password must be different from old one.");
        this.password = newPassword;
    }
    
    
    
    public static Users findById(String id){
        Query q = new Query(FileManager.FILES.USERS);
        q.addQuery("id", id);
        String row = FileManager.findSingleRow(q);
        if (row == null) return null;
        return parseUser(row);   
    }

    
    public static ArrayList<Users> findAll(Query q) {
    q.defineQueryRules(FileManager.FILES.USERS);
    String[] rows = FileManager.findRows(q);
    ArrayList<Users> users = new ArrayList<>();
    for (String row : rows) {
        users.add(parseUser(row));
    }

    return users;
}
    
    public void save(){
    // Convert user to a row
    String row = this.id + "|" + this.name + "|" + this.email + "|" + this.password;
    // Create a query for the USERS file
    Query q = new Query(FileManager.FILES.USERS);
    // Tell the query what row to insert
    q.addRowToInsert(row);
    // Insert the row
    FileManager.insertRow(q.getFilePath(), row);

    }

    @Override
    public String toString() {
        return "Name: " + name + "\nID: " + id + "\nEmail: " + email;
    }
    
    private static Users parseUser(String row) {
    String[] parts = row.split("\\|");

    Users u = new Users(parts[1], parts[2], parts[3]);
    u.id = new UID(parts[0]); // FIXED

    return u;
}
}

 