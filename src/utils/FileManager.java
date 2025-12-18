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
import java.time.LocalDateTime;
import java.util.HashMap;
import utils.Query;

public class FileManager {
    public static enum FILES {
        USERS(".\\db\\users.txt",
            new String[] {"id", "name", "email", "isAdmin", "password"},
            new HashMap<String, FileField>() {{
                put("id",       new FileField("id", "UID", true, true, 0));
                put("name",     new FileField("name", "string", false, false, 1));
                put("email",    new FileField("email", "email", false, true, 2));
                put("isAdmin",  new FileField("isAdmin", "boolean", false, false, 3));
                put("password", new FileField("password", "string", false, false, 4));
            }}
        ),

        EMPLOYEES(".\\db\\employees.txt",
            new String[] {"id", "name", "email", "password", "type", "creator_id"},
            new HashMap<String, FileField>() {{
                put("id",         new FileField("id", "UID", true, true, 0));
                put("name",       new FileField("name", "string", false, false, 1));
                put("email",      new FileField("email", "email", false, true, 2));
                put("password",   new FileField("password", "string", false, false, 3));
                put("type",   new FileField("type", "string", false, false, 4));
                put("creator_id", new FileField("creator_id", "UID", false, false, 5));
            }}
        ),

        PROJECTS(".\\db\\projects.txt",
            new String[] {"id", "name", "desc", "leader_id"},
            new HashMap<String, FileField>() {{
                put("id",          new FileField("id", "UID", true, true, 0));
                put("name",        new FileField("name", "string", false, false, 1));
                put("desc",        new FileField("desc", "string", false, false, 2));
                put("leader_id",   new FileField("leader_id", "UID", false, false, 3));
            }}
        ),

        TASKS(".\\db\\tasks.txt",
            new String[] {
                "id", "title", "desc", "priority", "phase",
                "creator_id",
                "project_id", "employee_id"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true, 0));
                put("title",         new FileField("title", "string", false, false, 1));
                put("desc",          new FileField("desc", "string", false, false, 2));
                put("priority",      new FileField("priority", "string", false, false, 3));
                put("phase",         new FileField("phase", "string", false, false, 4));
                put("creator_id",    new FileField("leader_id", "UID", false, false, 5));
                put("project_id",    new FileField("project_id", "UID", false, false, 6));
                put("employee_id",   new FileField("employee_id", "UID", false, false, 7));
            }}
        ),

        TASKS_ATTEMPTS(".\\db\\task_attempts.txt",
            new String[] {
                "id", "task_id", "employee_id", "start_date",
                "end_date", "status"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true, 0));
                put("task_id",       new FileField("task_id", "UID", false, false, 1));
                put("employee_id",   new FileField("employee_id", "UID", false, false, 2));
                put("start_date",    new FileField("start_date", "datetime", false, false, 3));
                put("end_date",      new FileField("end_date", "datetime", false, false, 4));
                put("status",        new FileField("status", "string", false, false, 5));
            }}
        ),

        PERMISSION_REQS(".\\db\\permission_reqs.txt",
            new String[] {
                "id", "title", "reason", "employee_id",
                "employee_name", "from_date", "to_date",
                "status"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true, 0));
                put("title",         new FileField("title", "string", false, false, 1));
                put("reason",          new FileField("desc", "string", false, false, 2));
                put("employee_id",   new FileField("employee_id", "UID", false, false, 3));
                put("from_date",     new FileField("from_date", "datetime", false, false, 4));
                put("to_date",       new FileField("to_date", "datetime", false, false, 5));
                put("status",        new FileField("status", "string", false, false, 6));
            }}
        ),

        MISSION_REQS(".\\db\\mission_reqs.txt",
            new String[] {
                "id", "title", "reason", "employee_id",
                "from_date", "to_date", "status"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true, 0));
                put("title",         new FileField("title", "string", false, false, 1));
                put("reason",          new FileField("desc", "string", false, false, 2));
                put("employee_id",   new FileField("employee_id", "UID", false, false, 3));
                put("from_date",     new FileField("from_date", "datetime", false, false, 4));
                put("to_date",       new FileField("to_date", "datetime", false, false, 5));
                put("status",        new FileField("status", "string", false, false, 6));
            }}
        ),

        LEAVE_REQS(".\\db\\leave_reqs.txt",
            new String[] {
                "id", "title", "reason", "employee_id",
                "from_date", "to_date", "status", "leave_type"
            },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true, 0));
                put("title",         new FileField("title", "string", false, false, 1));
                put("reason",        new FileField("reason", "string", false, false, 2));
                put("employee_id",   new FileField("employee_id", "UID", false, false, 3));
                put("from_date",     new FileField("from_date", "datetime", false, false, 4));
                put("to_date",       new FileField("to_date", "datetime", false, false, 5));
                put("status",        new FileField("status", "string", false, false, 6));
                put("leave_type",    new FileField("leave_type", "string", false, false, 7));
            }}
        ),

        TIMECARDS(".\\db\\timecards.txt",
            new String[] { "id", "check_in", "check_out", "employee_id" },
            new HashMap<String, FileField>() {{
                put("id",            new FileField("id", "UID", true, true, 0));
                put("check_in",      new FileField("check_in", "date", false, false, 1));
                put("check_out",     new FileField("check_out", "date", false, false, 2));
                put("employee_id",   new FileField("employee_id", "UID", false, false, 3));
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
        File file = safeFileMutaion(q.getFilePath());
        
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
        return null;
    }
    public static String[] findRows(Query q) {
        File file = safeFileMutaion(q.getFilePath());
        
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
        File file = safeFileMutaion(q.getFilePath());
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
    public static String insertRow(Query q) throws FileManagerException {
        if (q == null || q.getFilePath() == null || q.getRowToInsert() == null) {
            return "";
        }
        String errors = checkDataMutation(q, q.getRowToInsert());
        if(errors instanceof String) throw new FileManagerException(errors);

        File file = safeFileMutaion(q.getFilePath());
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(q.getRowToInsert());
            return q.getRowToInsert();
        } catch (IOException ex) {
            System.getLogger(FileManager.class.getName())
                  .log(System.Logger.Level.ERROR, "Failed to insert row into file: " + file.getAbsolutePath(), ex);
        }

        return null;
    }
    public static boolean deleteRow(Query query) {
        safeFileMutaion(query.getFilePath());
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

            Files.write(Paths.get(query.getFilePath()), result, StandardOpenOption.TRUNCATE_EXISTING);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static File safeFileMutaion(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                file.createNewFile();
            } catch (IOException ex) {
                System.getLogger(FileManager.class.getName())
                    .log(System.Logger.Level.ERROR, "Failed to create file: " + file.getAbsolutePath(), ex);
                return null;
            }
        }
        return file;
    }
    private static String checkDataMutation(Query q, String row) {
        FileManager.FILES rules = q.getQueryRules();
        String[] parts = Query.splitRow(row);
        if(parts.length != rules.fieldIDs.length) {
            return "Missing Fields";
        }
        List<String> errors = new ArrayList<>();
        rules.fields.forEach((key, value) -> {
            FileField field = rules.fields.get(key);
            String rawValue = parts[field.getPosIndex()].trim();
            System.out.println(field.getPosIndex() + " " + key + " "+ value + " " + rawValue);
            if(field.isIndex()) {
                Query redundantQuery = new Query(q.getQueryRules());
                redundantQuery.addQuery(field.getId(), rawValue);
                System.out.println(Arrays.asList(FileManager.findRows(redundantQuery)));
                if(FileManager.findRows(redundantQuery).length >= 1) {
                    errors.add("DUPLICATE INDEX: " + rawValue + " could not duplicated");
                }
            }
            try {
                switch (field.getType()) {
                    case "string":
                        // nothing to parse
                        break;
                    case "boolean":
                        if (!rawValue.equalsIgnoreCase("true") &&
                            !rawValue.equalsIgnoreCase("false")) {
                            throw new IllegalArgumentException("must be true or false");
                        }
                        Boolean.parseBoolean(rawValue);
                        break;

                    case "UID":
                        if (!UID.isValid(rawValue)) {
                            throw new IllegalArgumentException("invalid UID format");
                        }
                        break;

                    case "email":
                        if (!rawValue.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
                            throw new IllegalArgumentException("invalid email format");
                        }
                        break;

                    case "date":
                        LocalDateTime.parse(rawValue);
                        break;
                        
                    case "datetime":
                        LocalDateTime.parse(rawValue);
                        break;

                    default:
                        errors.add(field.getId() + " has unknown type: " + field.getType());
                }

            } catch (Exception e) {
                errors.add(field.getId() + " (" + field.getType() + "): " + e.getMessage());
            }
        });
        // If errors concat a string and Return them
        return errors.isEmpty() ? null : String.join("\n", errors); 
    }
}
