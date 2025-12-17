import java.util.*;
   
       public class LeaveRequest {
    public enum TYPES {
        SICK,
        VACATION,
        EMERGENCY,
        UNPAID
    }
    public enum STATUSES {
        PENDING,
        APPROVED,
        REJECTED
    }
private UID id;
    private String title;
    private String reason;
    private TYPES leaveType;
    private DateTime from;
    private DateTime to;
    private STATUSES status;
    private Employee employee;
public LeaveRequestt(
            String title,
            String reason,
            DateTime from,
            DateTime to,
            Employee employee,
            TYPES type
    ) {
        this.id = new UID();
        this.title = title;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.employee = employee;
        this.leaveType = type;
        this.status = STATUSES.PENDING; 
    
public static LeaveRequest parseLeaveRequest(String row) {
        String[] p = row.split("\\|");

        UID id = new UID(p[0]);
        String title = p[1];
        String reason = p[2];
        DateTime from = DateTime.parse(p[3]);
        DateTime to = DateTime.parse(p[4]);
        TYPES type = TYPES.valueOf(p[5]);
        STATUSES status = STATUSES.valueOf(p[6]);
        Employee emp = Employee.findById(p[7]);

        LeaveRequest lr = new LeaveRequest(title, reason, from, to, emp, type);
        lr.id = id;
        lr.status = status;

        return lr;
    }

public static LeaveRequest findById(String id) {
        Query q = new Query(FileManager.FILES.LEAVE_REQUESTS);
        q.addQuery("id", id);

        String row = FileManager.findSingleRow(q);
        if (row == null) return null;

        return parseLeaveRequest(row);
    }

public static ArrayList<LeaveRequest> findAll(Query q) {
        q.defineQueryRules(FileManager.FILES.LEAVE_REQUESTS);

        String[] rows = FileManager.findRows(q);
        ArrayList<LeaveRequest> list = new ArrayList<>();

        for (String row : rows) {
            list.add(parseLeaveRequest(row));
        }

        return list;
    }
public void changeStatus(STATUSES newStatus) {
        Query q = new Query(FileManager.FILES.LEAVE_REQUESTS);
        q.addQuery("id", id.toString());
        q.addUpdateClause("status", newStatus.name());

        FileManager.updateRows(q);

        this.status = newStatus;
    }

public String getTitle() {
        return title;
    }

    public String getReason() {
        return reason;
    }

    public TYPES getLeaveType() {
        return leaveType;
    }

    public DateTime getStartDate() {
        return from;
    }

    public DateTime getEndDate() {
        return to;
    }

    public Employee getEmployeeRequested() {
        return employee;
    }



    

     
    


