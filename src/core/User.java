package core;

import utils.PasswordException;

/**
 *
 * @author FatmaAli
 */
public class User {
    protected String id; 
    protected String name;
    protected String email;
    protected String password;

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
    protected String getPassword() {
        return email;
    }

    
    
    public boolean comparePasswordWith(String pswd) {
      return !pswd.equals(this.password);
    }

    public void changePassword(String newPassword) throws PasswordException {
        if ( newPassword == null || newPassword.length() < 6)
            throw new PasswordException("Password must at least contain 6 characters.");
        else if (newPassword.equals(password))
            throw new PasswordException("Password must be different from old one.");
        this.password = newPassword;
    }

    @Override
    public String toString() {
        return "User{ id:" + id + " name: " + name + " email: " + email + " }";
    }
    
    
}

 