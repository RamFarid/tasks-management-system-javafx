package core;

import java.time.LocalDateTime;
import utils.PasswordException;
import java.util.Date;
import java.util.ArrayList;
import utils.FileManager;
import utils.Query;
import utils.UID;

public class Employee extends User {
   private String type;
   private Admin creator;
   
   public Employee(String id, String name, String email, String password, String type, Admin creator) throws PasswordException {
        super(id, name, email, password);
        this.type = type;
        this.creator = creator;
    }

    public String getType() {
        return type;
    }

    public Admin getCreator() {
        return creator;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreator(Admin creator) {
        this.creator = creator;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    public static Employee parseEmployee(String row) {
        String[] parts = row.split("\\|");
        Admin admin = Admin.findById(parts[5]);
        Employee u = new Employee(parts[0], parts[1], parts[2], parts[3], parts[4], admin);
        return u;
    }
   
    public static Employee findById(String id){
        Query q = new Query(FileManager.FILES.EMPLOYEES);
        q.addQuery("id", id);
        String row = FileManager.findSingleRow(q);
        if (row == null) return null;
        return parseEmployee(row);   
    }
    
    public static ArrayList<Employee> findAll(Query q) {
        q.defineQueryRules(FileManager.FILES.EMPLOYEES);
        String[] rows = FileManager.findRows(q);
        ArrayList<Employee> users = new ArrayList<>();
        for (String row : rows) {
            users.add(parseEmployee(row));
        }

        return users;
    }
   
   public PermissionRequest requestPermission(
            String title, 
            String reason, 
            LocalDateTime from, 
            LocalDateTime to
    ) {
        // (String id, String title, String reason, 
        // LocalDateTime from, LocalDateTime to, Employee e, String status)
        PermissionRequest req = new PermissionRequest(UID.generate(), title,
                reason, from, to, this);
        req.save();
        return req;
    }
   public MissionRequest requestMission(
        String title, 
        String reason, 
        LocalDateTime from, 
        LocalDateTime to) {
        // (String id, String title, String reason, 
        // LocalDateTime from, LocalDateTime to, Employee e, String status)
        MissionRequest req = new MissionRequest(UID.generate(), title, reason, 
                from, to, this);
        req.save();
        return req;
    }
   //private Tasks task;
   public LeaveRequest requestLeave(
        String title, 
        String reason, 
        LocalDateTime from, 
        LocalDateTime to, 
        LeaveRequest.LEAVE_TYPES type) {
        // String id, String title, String reason,
        // LocalDateTime from, LocalDateTime to, Employee employee,
        //  String type, String status?
        LeaveRequest req = new LeaveRequest(UID.generate(), title, reason, 
                from, to, this, type);
        req.save();
        return req;
    }
    public ArrayList<LeaveRequest> getLeaveReqs() {
        Query q = new Query(FileManager.FILES.LEAVE_REQS);
        q.addQuery("employee_id", this.id);
        return LeaveRequest.findAll(q);
    }
    public ArrayList<MissionRequest> getMissionReqs() {
        Query q = new Query(FileManager.FILES.MISSION_REQS);
        q.addQuery("employee_id", this.id);
        return MissionRequest.findAll(q);
    }
    public ArrayList<PermissionRequest> getPermissionReqs() {
        Query q = new Query(FileManager.FILES.PERMISSION_REQS);
        q.addQuery("employee_id", this.id);
        return PermissionRequest.findAll(q);
    }
    
    public ArrayList<Task> getTasks() {
        Query q = new Query(FileManager.FILES.TASKS);
        q.addQuery("employee_id", this.id);
        return Task.findAll(q);
    }
    

    public void save() {
        String row = id.toString() + "|" 
                   + name + "|" 
                   + email + "|" 
                   + password + "|" 
                   + type + "|" 
                   + creator.getId().toString();

        Query q = new Query(FileManager.FILES.EMPLOYEES);
        q.addRowToInsert(row);

        FileManager.insertRow(q);
    }
    
}