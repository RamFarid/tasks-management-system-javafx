package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import utils.Query;

public class FileManager {
    public static enum FILES {
        USERS(".\\db\\users.txt",
            new String[] {"id", "name", "email", "isAdmin", "password"},
            new HashMap<String, FileField>() {{
                put("id",       new FileField("id", "UID", true, true));
                put("name",     new FileField("name", "string", false, false));
                put("email",    new FileField("email", "email", false, true));
                put("isAdmin",  new FileField("isAdmin", "boolean", false, false));
                put("password", new FileField("password", "string", false, false));
            }}
        ),

        EMPLOYEES(".\\db\\employees.txt",
            new String[] {"id", "name", "email", "password", "creator_id"},
            new HashMap<String, FileField>() {{
                put("id",         new FileField("id", "UID", true, true));
                put("name",       new FileField("name", "string", false, false));
                put("email",      new FileField("email", "email", false, true));
                put("password",   new FileField("password", "string", false, false));
                put("creator_id", new FileField("creator_id", "UID", false, false));
            }}
        ),

        PROJECTS(".\\db\\projects.txt",
            new String[] {"id", "name", "desc", "leader_id", "members", "leader_name"},
            new HashMap<String, FileField>() {{
                put("id",          new FileField("id", "UID", true, true));
                put("name",        new FileField("name", "string", false, false));
                put("desc",        new FileField("desc", "string", false, false));
                put("leader_id",   new FileField("leader_id", "UID", false, false));
                put("members",     new FileField("members", "UID[]", false, false));
                put("leader_name", new FileField("leader_name", "string", false, false));
            }}
        ),

        TASKS(".\\db\\tasks.txt",
            new String[] {
                "id", "title", "desc", "priority", "phase",
                "leader_name", "employee_name", "leader_id",
                "project_id", "employee_id"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true));
                put("title",         new FileField("title", "string", false, false));
                put("desc",          new FileField("desc", "string", false, false));
                put("priority",      new FileField("priority", "string", false, false));
                put("phase",         new FileField("phase", "string", false, false));
                put("leader_name",   new FileField("leader_name", "string", false, false));
                put("employee_name", new FileField("employee_name", "string", false, false));
                put("leader_id",     new FileField("leader_id", "UID", false, false));
                put("project_id",    new FileField("project_id", "UID", false, false));
                put("employee_id",   new FileField("employee_id", "UID", false, false));
            }}
        ),

        TASKS_ATTEMPTS(".\\db\\task_attempts.txt",
            new String[] {
                "id", "task_id", "employee_id", "start_date",
                "end_date", "status", "task_title",
                "task_desc", "employee_name"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true));
                put("task_id",       new FileField("task_id", "UID", false, false));
                put("employee_id",   new FileField("employee_id", "UID", false, false));
                put("start_date",    new FileField("start_date", "date", false, false));
                put("end_date",      new FileField("end_date", "date", false, false));
                put("status",        new FileField("status", "string", false, false));
                put("task_title",    new FileField("task_title", "string", false, false));
                put("task_desc",     new FileField("task_desc", "string", false, false));
                put("employee_name", new FileField("employee_name", "string", false, false));
            }}
        ),

        PERMISSION_REQS(".\\db\\permission_reqs.txt",
            new String[] {
                "id", "title", "desc", "employee_id",
                "employee_name", "from_date", "to_date",
                "reason", "status"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true));
                put("title",         new FileField("title", "string", false, false));
                put("desc",          new FileField("desc", "string", false, false));
                put("employee_id",   new FileField("employee_id", "UID", false, false));
                put("employee_name", new FileField("employee_name", "string", false, false));
                put("from_date",     new FileField("from_date", "date", false, false));
                put("to_date",       new FileField("to_date", "date", false, false));
                put("status",        new FileField("status", "string", false, false));
                put("reason",        new FileField("reason", "string", false, false));
            }}
        ),

        MISSION_REQS(".\\db\\mission_reqs.txt",
            new String[] {
                "id", "title", "desc", "employee_id",
                "employee_name", "from_date", "to_date",
                "reason", "status"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true));
                put("title",         new FileField("title", "string", false, false));
                put("desc",          new FileField("desc", "string", false, false));
                put("employee_id",   new FileField("employee_id", "UID", false, false));
                put("employee_name", new FileField("employee_name", "string", false, false));
                put("from_date",     new FileField("from_date", "date", false, false));
                put("to_date",       new FileField("to_date", "date", false, false));
                put("status",        new FileField("status", "string", false, false));
                put("reason",        new FileField("reason", "string", false, false));
            }}
        ),

        LEAVE_REQS(".\\db\\mission_reqs.txt",
            new String[] {
                "id", "title", "desc", "employee_id",
                "employee_name", "from_date", "to_date",
                "reason", "status", "leave_type"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true));
                put("title",         new FileField("title", "string", false, false));
                put("desc",          new FileField("desc", "string", false, false));
                put("employee_id",   new FileField("employee_id", "UID", false, false));
                put("employee_name", new FileField("employee_name", "string", false, false));
                put("from_date",     new FileField("from_date", "date", false, false));
                put("to_date",       new FileField("to_date", "date", false, false));
                put("status",        new FileField("status", "string", false, false));
                put("reason",        new FileField("reason", "string", false, false));
                put("leave_type",    new FileField("leave_type", "string", false, false));
            }}
        ),

        TIMECARDS(".\\db\\timecards.txt",
            new String[] { "id", "check_in", "check_out", "employee_id", "employee_name" },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true));
                put("check_in",      new FileField("check_in", "date", false, false));
                put("check_out",     new FileField("check_out", "date", false, false));
                put("employee_id",   new FileField("employee_id", "UID", false, false));
                put("employee_name", new FileField("employee_name", "string", false, false));
            }}
        );

        public final String filePath;
        public final String[] fieldIDs;
        public final HashMap<String, FileField> fields;

        FILES(String path, String[] fieldIDs, HashMap<String, FileField> fields) {
            this.filePath = path;
            this.fieldIDs = fieldIDs;
            this.fields = fields;
        }

        @Override
        public String toString() {
            return "{ filePath: " + filePath + ", fieldIDs: " + Arrays.toString(fieldIDs) + " }";
        }
    }

    public static String findSingleRow(Query q) {
        File file = new File(q.getFilePath());
        
        try(
            Scanner reader = new Scanner(file);
        ){
            while(reader.hasNextLine()) {
                String row = reader.nextLine();
                if(q.isMatchedQuery(row)) return row;
            }
        } catch (FileNotFoundException ex) {
            System.getLogger(FileManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return "";
    }
    public static String[] findRows(Query q) {
        File file = new File(q.getFilePath());
        ArrayList<String> rows = new ArrayList<>();
        try(
            Scanner reader = new Scanner(file);
        ){
            while(reader.hasNextLine()) {
                String row = reader.nextLine();
                if(q.isMatchedQuery(row)) {
                    rows.add(row);
                }
            }
        } catch (FileNotFoundException ex) {
            System.getLogger(FileManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return rows.toArray(new String[0]);
    }
    public static String[] updateRows(Query q) {
        File file = new File(q.getFilePath());
        ArrayList<String> fileLines = new ArrayList<>();
        ArrayList<String> updatedRows = new ArrayList<>();
    
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String row = reader.nextLine();
                
                if (q.isMatchedQuery(row)) {
                    String newRow = q.updateRow(row); 
                    fileLines.add(newRow);
                    updatedRows.add(newRow);
                } else {
                    fileLines.add(row);
                }
            }
        } catch (FileNotFoundException ex) {
            System.getLogger(FileManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return new String[0]; // Return empty if file not found
        }
        
        try (
            FileWriter writer = new FileWriter(file, false);
            PrintWriter out = new PrintWriter(writer);
        ) {
            for (String line : fileLines) {
                out.println(line);
            }
        } catch (IOException ex) {
            System.getLogger(FileManager.class.getName()).log(System.Logger.Level.ERROR, "Error writing to file", ex);
        }

        return updatedRows.toArray(new String[0]);
    }
    public static String insertRow(Query q) {
        PrintWriter printWriter = null;
        PrintWriter writer = null;
        try {
            FileWriter fileWriter = new FileWriter(new File(q.getFilePath()), true);
            writer = new PrintWriter(fileWriter);
            writer.write(q.getRowToInsert() + "\n");
        } catch (FileNotFoundException ex) {
            System.getLogger(FileManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(FileManager.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            writer.close();
        }
        return "";
    }
    public static boolean deleteRow(Query query) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(query.getFilePath()));
            List<String> result = new ArrayList<>();
            if (!lines.isEmpty()) {
                result.add(lines.get(0));
            }

            for (int i = 1; i < lines.size(); i++) {
                String row = lines.get(i);
                if (!query.isMatchedQuery(row)) {
                    result.add(row);
                }
            }

            // Rewrite file
            Files.write(Paths.get(query.getFilePath()), result, StandardOpenOption.TRUNCATE_EXISTING);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
