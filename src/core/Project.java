package core;

import java.util.ArrayList;
import utils.FileManager;
import utils.Query;

public class Project {
    private String id;
    private String name;
    private String description;
    private Admin leader;

    public Project(String id, String name, String description, Admin leader) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.leader = leader;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Admin getLeader() {
        return leader;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLeader(Admin leader) {
        this.leader = leader;
    }
    
    public static Project findById(String id){
        Query q = new Query(FileManager.FILES.PROJECTS);
        q.addQuery("id", id);
        String row = FileManager.findSingleRow(q);
        if (row == null) return null;
        return parseProject(row);   
    }
    
    public static ArrayList<Project> findAll(Query q) {
        q.defineQueryRules(FileManager.FILES.PROJECTS);
        String[] rows = FileManager.findRows(q);
        ArrayList<Project> prjcs = new ArrayList<>();
        for (String row : rows) {
            prjcs.add(parseProject(row));
        }

        return prjcs;
    }
     
    public static Project parseProject(String row) {
        String[] parts = row.split("\\|");
        Admin admin = Admin.findById(parts[3]);
        Project u = new Project(parts[0], parts[1], parts[2], admin);
        return u;
    }
}
