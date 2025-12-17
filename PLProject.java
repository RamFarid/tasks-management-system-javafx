
package PLProject;

import java.math.BigDecimal;

/**
 *
 * @author reddy
 */
public class PLProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            Admin a = new Admin(true, "Laila Shreif", "laila@gmail.com", "lail");
            Employee one = new Employee("officer", a, "Hany Ahmed", "Hany@gmail.com", "Hany123");
            System.out.println(one.toString());
            System.out.println(a.toString());
            System.out.println(one.getCreator());
        
        
        
    }
    
}
