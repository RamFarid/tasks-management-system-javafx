package core;

import core.Employee;
import java.time.LocalDateTime;
import java.util.ArrayList;
import utils.FileManager;
import utils.Query;

public class LeaveRequest {
    public enum LEAVE_TYPES {
        SICK,
        VACATION,
        EMERGENCY,
        UNPAID,
        ANNUAL,
        HAJJ,
        MATERNITY,
    }
    public enum STATUSES {
        PENDING,
        APPROVED,
        REJECTED
    }
    private String id;
    private String title;
    private String reason;
    private LocalDateTime from;
    private LocalDateTime to;
    private Employee employee;
    private LEAVE_TYPES leaveType;
    private STATUSES status;
    
    
    public LeaveRequest(String id, String title, String reason,
            LocalDateTime from, LocalDateTime to, Employee employee,
            String type, String status) throws IllegalArgumentException {
        this.id = id;
        this.title = title;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.employee = employee;
        this.leaveType = LEAVE_TYPES.valueOf(type);
        this.status = STATUSES.valueOf(status);
    }
    public LeaveRequest(String id, String title, String reason,
            LocalDateTime from, LocalDateTime to, Employee employee,
            LEAVE_TYPES type) {
        this.id = id;
        this.title = title;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.employee = employee;
        this.leaveType = type;
        this.status = STATUSES.PENDING;
    }

    
    
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getReason() {
        return reason;
    }
    public LocalDateTime getFrom() {
        return from;
    }
    public LocalDateTime getTo() {
        return to;
    }
    public Employee getEmployee() {
        return employee;
    }
    public LEAVE_TYPES getLeaveType() {
        return leaveType;
    }
    public STATUSES getStatus() {
        return status;
    }
    
    public static LeaveRequest parseLeaveRequest(String row) {
        String[] parts = row.split("\\|");
        Employee e = Employee.findById(parts[3]);
        LeaveRequest lr = new LeaveRequest(
                parts[0], parts[1], parts[2],
                LocalDateTime.parse(parts[4]), LocalDateTime.parse(parts[5]), 
                e, parts[7], parts[6]);
        return lr;
    }

    public static LeaveRequest findById(String id) {
        Query q = new Query(FileManager.FILES.LEAVE_REQS);
        q.addQuery("id", id);

        String row = FileManager.findSingleRow(q);
        if (row == null) return null;

        return parseLeaveRequest(row);
    }

    public static ArrayList<LeaveRequest> findAll(Query q) {
        q.defineQueryRules(FileManager.FILES.LEAVE_REQS);

        String[] rows = FileManager.findRows(q);
        ArrayList<LeaveRequest> list = new ArrayList<>();

        for (String row : rows) {
            list.add(parseLeaveRequest(row));
        }

        return list;
    }
    
    public void changeStatus(STATUSES newStatus) {
        Query q = new Query(FileManager.FILES.LEAVE_REQS);
        q.addQuery("id", id.toString());
        q.addUpdateClause("status", newStatus.name());

        FileManager.updateRows(q);

        this.status = newStatus;
    }
    
    public void save() {
        String row = this.id + "|" 
                   + this.title + "|" 
                   + this.reason + "|" 
                   + this.employee.getId() + "|" 
                   + this.from + "|"
                   + this.to + "|"
                   + this.status + "|"
                   + this.getLeaveType();

        Query q = new Query(FileManager.FILES.LEAVE_REQS);
        q.addRowToInsert(row);

        FileManager.insertRow(q);
    }
}



    

     
    

