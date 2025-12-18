package core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import utils.FileManager;
import utils.Query;

public class PermissionRequest {
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
    private STATUSES status;
    
    public PermissionRequest(String id, String title, String reason, 
            LocalDateTime from, LocalDateTime to, 
            Employee e, String status) {
        this.id = id;
        this.title = title;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.employee = e;
        this.status = STATUSES.valueOf(status);
    }
    
    public PermissionRequest(String id, String title, String reason, LocalDateTime from, LocalDateTime to, Employee e) {
        this.id = id;
        this.title = title;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.employee = e;
        this.status = STATUSES.PENDING;
    }
    
    public static PermissionRequest parsePermissionRequest(String row) {
        String[] parts = row.split("\\|");
        Employee e = Employee.findById(parts[3]);
        PermissionRequest pr = new PermissionRequest(parts[0], parts[1],
                parts[2], LocalDateTime.parse(parts[4]),
                LocalDateTime.parse(parts[5]), e);
        return pr;
    }

    public static PermissionRequest findById(String id) {
        Query q = new Query(FileManager.FILES.PERMISSION_REQS);
        q.addQuery("id", id);

        String row = FileManager.findSingleRow(q);
        if (row == null) return null;

        return parsePermissionRequest(row);
    }

    public static ArrayList<PermissionRequest> findAll(Query q) {
        q.defineQueryRules(FileManager.FILES.PERMISSION_REQS);

        String[] rows = FileManager.findRows(q);
        ArrayList<PermissionRequest> list = new ArrayList<>();

        for (String row : rows) {
            list.add(parsePermissionRequest(row));
        }

        return list;
    }
    
    public void changeStatus(PermissionRequest.STATUSES newStatus) {
        Query q = new Query(FileManager.FILES.PERMISSION_REQS);
        q.addQuery("id", this.id);
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
                   + this.status;

        Query q = new Query(FileManager.FILES.PERMISSION_REQS);
        q.addRowToInsert(row);

        FileManager.insertRow(q);
    }
}
