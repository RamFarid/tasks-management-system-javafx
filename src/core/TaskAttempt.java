package core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import utils.FileManager;
import utils.Query;

public class TaskAttempt {
    private String id;
    private Task task;
    private Employee employee;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Task.PHASES status;

    public TaskAttempt(String id, Task task, 
            Employee employee, LocalDateTime startDate, 
            LocalDateTime endDate, String status) {
        this.id = id;
        this.task = task;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = Task.PHASES.valueOf(status);
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public void setStatus(Task.PHASES status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public Task getTask() {
        return task;
    }
    public Employee getEmployee() {
        return employee;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public Task.PHASES getStatus() {
        return status;
    }
    
    public static TaskAttempt findById(String id){
        Query q = new Query(FileManager.FILES.TASKS_ATTEMPTS);
        q.addQuery("id", id);
        String row = FileManager.findSingleRow(q);
        if (row.length() <= 0) return null;
        return parseTaskAttempt(row);   
    }
    
    public static ArrayList<TaskAttempt> findAll(Query q) {
        q.defineQueryRules(FileManager.FILES.TASKS_ATTEMPTS);
        String[] rows = FileManager.findRows(q);
        ArrayList<TaskAttempt> taskAttempts = new ArrayList<>();
        for (String row : rows) {
            taskAttempts.add(parseTaskAttempt(row));
        }

        return taskAttempts;
    }
    
    public static TaskAttempt parseTaskAttempt(String row) {
        String[] parts = row.split("\\|");
        Task task = Task.findById(parts[1]);
        Employee employee = Employee.findById(parts[2]);
        TaskAttempt taskAtt = new TaskAttempt(parts[0], task, employee, 
                LocalDateTime.parse(parts[3]),
                LocalDateTime.parse(parts[4]),
                parts[5]);

        return taskAtt;
    }

    public void save() {
        String row = id.toString() + "|" 
           + this.task.getId() + "|" 
           + this.employee.getId() + "|" 
           + this.getStartDate() + "|" 
           + this.getEndDate() + "|" 
           + this.getStatus();

        Query q = new Query(FileManager.FILES.TASKS_ATTEMPTS);
        q.addRowToInsert(row);
        
        this.task.setPhase(this.getStatus());
        this.task.save();
        
        FileManager.insertRow(q);
    }
}
