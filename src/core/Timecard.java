package core;

import java.util.ArrayList;
import utils.FileManager;
import utils.Query;
import utils.UID;
import core.Employee;
import java.time.LocalDateTime;

public class Timecard {
    private boolean isExisted = false;
    private String id = UID.generate();
    private Employee employee;
    private LocalDateTime checkin;
    private LocalDateTime checkout;

    public Timecard(String id, LocalDateTime checkin, LocalDateTime checkout, Employee employee) {
        this.employee = employee;
        this.checkin = checkin;
        this.checkout = checkout;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getCheckin() {
        return checkin;
    }

    public LocalDateTime getCheckout() {
        return checkout;
    }
    
    public static ArrayList<Timecard> findAll(Query q) {
        String[] rows = FileManager.findRows(q);
        ArrayList<Timecard> timecards = new ArrayList<>();
        for (String row : rows) {
            String[] splittedTC = Query.splitRow(row);
            timecards.add(parseTimecard(row));
        }
        return timecards;
    }
    
    public static Timecard parseTimecard(String row) {
        String[] parts = row.split("\\|");
        Employee e = Employee.findById(parts[3]);
        Timecard u = new Timecard(parts[0],
                LocalDateTime.parse(parts[1]),
                LocalDateTime.parse(parts[2]),
                e);

        return u;
    }
    
    public static Timecard findById(String id) {
        Query q = new Query(FileManager.FILES.TIMECARDS);
        q.addQuery("id", id);
        String timecard = FileManager.findSingleRow(q);
        return parseTimecard(timecard);
    }
    
    public void save() {
        Query q = new Query(FileManager.FILES.USERS);
        q.addRowToInsert(this.id + "|" +
                this.checkin + "|" +
                this.checkout + "|" +
                this.employee.id + "|" +
                this.employee.name);
        FileManager.insertRow(q);
    }
}
