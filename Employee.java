
package PLProject;

/**
 *
 * @author FatmaAli
 */
public class Employee extends Users{
   private String type;
   private Admin creator;
   
   public Employee(String type, Admin creator, String name, String email, String password) throws PasswordException {
        super(name, email, password);
        if (password.length() < 6)
            throw new PasswordException("Password must at least contain 6 characters.");
        this.type = type;
        this.creator = creator;
    }
   
   public PermissionRequest requestPermission(
        String title, 
        String reason, 
        DateTime from, 
        DateTime to) {

    PermissionRequest req = new PermissionRequest(this, title, reason, from, to);
    req.save();
    return req;
}
   public MissionRequest requestMission(
        String title, 
        String reason, 
        DateTime from, 
        DateTime to) {

    MissionRequest req = new MissionRequest(this, title, reason, from, to);
    req.save();
    return req;
}
   //private Tasks task;
   public LeaveRequest requestLeave(
        String title, 
        String reason, 
        DateTime from, 
        DateTime to, 
        LeaveRequest.Types type) {

    LeaveRequest req = new LeaveRequest(this, title, reason, from, to, type);
    req.save();
    return req;
}

    

    public void setCreator(Admin creator) {
        this.creator = creator;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Admin getCreator() {
        return creator;
    }

    public String getType() {
        return type;
    }
    public ArrayList<LeaveRequest> getLeaveReqs() {
    Query q = new Query(FileManager.FILES.LEAVE_REQUESTS);
    q.addQuery("employeeId", this.id.toString());
    return LeaveRequest.findAll(q);
}
    public ArrayList<MissionRequest> getMissionReqs() {
    Query q = new Query(FileManager.FILES.MISSION_REQUESTS);
    q.addQuery("employeeId", this.id.toString());
    return MissionRequest.findAll(q);
}
    public ArrayList<PermissionRequest> getPermissionReqs() {
    Query q = new Query(FileManager.FILES.PERMISSION_REQUESTS);
    q.addQuery("employeeId", this.id.toString());
    return PermissionRequest.findAll(q);
}
   public ArrayList<Task> getTasks() {
    Query q = new Query(FileManager.FILES.TASKS);
    q.addQuery("employeeId", this.id.toString());
    return Task.findAll(q);
}
    
@Override
public void save() {
    String row = id.toString() + "|" 
               + name + "|" 
               + email + "|" 
               + password + "|" 
               + type + "|" 
               + creator.getId().toString();

    Query q = new Query(FileManager.FILES.EMPLOYEES);
    q.addRowToInsert(row);

    FileManager.insertRow(q.getFilePath(), row);
}
    
}
