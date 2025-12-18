package core;
import core.Admin;
import core.Employee;
import java.util.ArrayList;
import utils.FileManager;
import utils.Query;

public class Task {
    public enum PHASES {
       PENDING,
       APPROVED,
       REJECTED
    }
    public enum PRIORITIES {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }
    private String id;
    private String title;
    private String description;
    private PRIORITIES priority;
    private PHASES phase;
    private Admin creator;
    private Project project;
    private Employee assignedEmployee;

    public Task(String id, String title, String description, 
            String priority, String phase, Admin creator,
            Project project, Employee assignedEmployee) {
       this.id = id;
       this.title = title;
       this.description = description;
       this.priority = PRIORITIES.valueOf(priority);
       this.phase = PHASES.valueOf(phase);
       this.creator = creator;
       this.project = project;
       this.assignedEmployee = assignedEmployee;
   }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(PRIORITIES priority) {
        this.priority = priority;
    }

    public void setPhase(PHASES phase) {
        this.phase = phase;
    }

    public void setCreator(Admin creator) {
        this.creator = creator;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    
    
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public PRIORITIES getPriority() {
        return priority;
    }

    public PHASES getPhase() {
        return phase;
    }

    public Admin getCreator() {
        return creator;
    }

    public Project getProject() {
        return project;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }
    
   
    
   public static Task findById(String id){
        Query q = new Query(FileManager.FILES.TASKS);
        q.addQuery("id", id);
        String row = FileManager.findSingleRow(q);
        if (row.length() <= 0) return null;
        return parseTask(row);   
    }
    
    public static ArrayList<Task> findAll(Query q) {
        q.defineQueryRules(FileManager.FILES.TASKS);
        String[] rows = FileManager.findRows(q);
        ArrayList<Task> tasks = new ArrayList<>();
        for (String row : rows) {
            tasks.add(parseTask(row));
        }

        return tasks;
    }
    
    public static Task parseTask(String row) {
        String[] parts = row.split("\\|");
        Admin admin = Admin.findById(parts[5]);
        Project project = Project.findById(parts[6]);
        Employee employee = Employee.findById(parts[7]);
        Task task = new Task(parts[0], parts[1], parts[2], 
                parts[3], parts[4], admin, project,
                employee);

        return task;
    }
    
    public String toRow(boolean withNames) {
        return id + "|" 
           + title + "|" 
           + description + "|" 
           + priority + "|" 
           + phase + "|"
           + this.project.getName()+ "|" 
           + this.assignedEmployee.getName();
    }

    public void save() {
        String row = id + "|" 
           + title + "|" 
           + description + "|" 
           + priority + "|" 
           + phase + "|" 
           + this.creator.getId() + "|" 
           + this.project.getId() + "|" 
           + this.assignedEmployee.getId();

        Query q = new Query(FileManager.FILES.TASKS);
        q.addRowToInsert(row);

        FileManager.insertRow(q);
    }
   
}