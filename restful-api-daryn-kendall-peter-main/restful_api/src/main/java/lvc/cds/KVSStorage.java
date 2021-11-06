package lvc.cds;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;

import javax.json.JsonObject;

import lvc.cds.KVS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class KVSStorage {
    
    static KVS projectKVS;
    static KVS taskKVS;
    static KVS todolistKVS;
    static KVS userKVS;
    static String filePath;

    public KVSStorage(String path){
        projectKVS = new KVS();
        taskKVS = new KVS();
        todolistKVS = new KVS();
        userKVS = new KVS();
        filePath = path;
        try {
            projectKVS.readInFile(path+"/projects.json"); 
        } catch (FileNotFoundException e) {
        }
        try {
            taskKVS.readInFile(path+"/tasks.json");
        } catch (FileNotFoundException e) {
        }
        try {
            todolistKVS.readInFile(path+"/todolists.json");
        } catch (FileNotFoundException e) {
        }
        try {
            userKVS.readInFile(path+"/users.json");
        } catch (FileNotFoundException e) {
        }
    }

    public static JSONArray getUsers() {
        var users = new JSONArray();
        for(int i = 1; i<=userKVS.getSize(); i++){
            users.add(userKVS.getValue(String.valueOf(i)));
        }
        return users;
    }

    public static JSONArray getProjects() {
        var projects = new JSONArray();
        for(int i = 1; i<=projectKVS.getSize(); i++){
            projects.add(projectKVS.getValue(String.valueOf(i)));
        }
        return projects;
    }

    public static JSONArray getTasks() {
        var tasks = new JSONArray();
        for(int i = 1; i<=taskKVS.getSize(); i++){
            tasks.add(projectKVS.getValue(String.valueOf(i)));
        }
        return tasks;
    }

    public static JSONArray getTodolists() {
        var todolists = new JSONArray();
        for(int i = 1; i<=todolistKVS.getSize(); i++){
            todolists.add(todolistKVS.getValue(String.valueOf(i)));
        }
        return todolists;
    }

    public static int addProject(Project p) {
        int id = projectKVS.getSize() +1;
        p.setId(id);
        p.setLink("http://localhost:8080/projects/"+id);
        projectKVS.add(String.valueOf(id), p.toJSON());
        projectKVS.writeOutFile(filePath+"/projects.json");
        return id;
    }

    public static int addTodolist(TodoList l) {
        int id = todolistKVS.getSize() +1;
        l.setId(id);
        l.setLink("http://localhost:8080/todolists/"+id);
        todolistKVS.add(String.valueOf(id), l.toJSON());
        todolistKVS.writeOutFile(filePath+"/todolists.json");
        return id;
    }

    public static int addTask(Task t) {
        int id = taskKVS.getSize() +1;
        t.setId(id);
        t.setLink("http://localhost:8080/tasks/"+id);
        taskKVS.add(String.valueOf(id), t.toJSON());
        taskKVS.writeOutFile(filePath+"/tasks.json");
        return id;
    }

    public static int addUser(User u) {
        int id = userKVS.getSize() +1;
        u.setId(id);
        u.setLink("http://localhost:8080/users/"+id);
        userKVS.add(String.valueOf(id), u.toJSON());
        userKVS.writeOutFile(filePath+"/users.json");
        return id;
    }

    public static Project getProject(int id){
        var ret = new Project();
        var project = projectKVS.getValue(String.valueOf(id));
        String name = project.get("name").toString();
        var assoc_todolist = project.get("associated_todolist");
        String link = project.get("link").toString();
        String status = project.get("status").toString();
        var associated_users = (JSONArray) project.get("associated_users");
        ret.setName(name);
        ret.setId(id);
        ret.setAssociatedUsers(associated_users);
        ret.setStatus(status);
        ret.setLink(link);
        if(assoc_todolist!=null){
            var associated_todolist = (JSONObject) assoc_todolist;
            ret.setAssociatedTodoList(associated_todolist);
        }
        return ret;
    }

    public static TodoList getTodolist(int id) {
        var todolist = todolistKVS.getValue(String.valueOf(id));
        String name = todolist.get("name").toString();
        var assoc_project = todolist.get("associated_project");
        var associated_tasks = (JSONArray) todolist.get("associated_tasks");
        String link = todolist.get("link").toString();
        String status = todolist.get("status").toString();
        var ret = new TodoList();
        ret.setName(name);
        ret.setId(id);
        ret.setAssociatedTasks(associated_tasks);
        ret.setStatus(status);
        ret.setLink(link);
        if(assoc_project!=null){
            var associated_project = (JSONObject) assoc_project;
            ret.setAssociatedProject(associated_project);
        }
        
        return ret;
    }

    public static Task getTask(int id) {
        var task = taskKVS.getValue(String.valueOf(id));
        String name = task.get("name").toString();
        var assoc_todolist = task.get("associated_todolist");
        var assoc_user = task.get("associated_user");
        String link = task.get("link").toString();
        String status = task.get("status").toString();
        String description = task.get("description").toString();
        var ret = new Task();
        ret.setName(name);
        ret.setId(id);
        ret.setStatus(status);
        ret.setLink(link);
        ret.setDescription(description);
        if(assoc_todolist!=null){
            var associated_todolist = (JSONObject) assoc_todolist;
            ret.setAssociatedTodoList(associated_todolist);
        }
        if(assoc_user!=null){
            var associated_user = (JSONObject) assoc_user;
            ret.setAssociatedUser(associated_user);
        }
        return ret;
    }

    public static User getUser(int id) {
        var user = userKVS.getValue(String.valueOf(id));
        String name = user.get("name").toString();
        var associated_projects = (JSONArray) user.get("associated_projects");
        var associated_tasks = (JSONArray) user.get("associated_tasks");
        String link = user.get("link").toString();
        var ret = new User();
        ret.setName(name);
        ret.setId(id);
        ret.setLink(link);
        ret.setAssociatedProjects(associated_projects);
        ret.setAssociatedTasks(associated_tasks);
        return ret;
    }

    public static void adjustProject(int id, String key, String value) {
        var project = projectKVS.getValue(String.valueOf(id));
        project.put(key,value);
        projectKVS.setValue(String.valueOf(id), project);
        projectKVS.writeOutFile(filePath+"/projects.json");
        var associated_todolist = project.get("associated_todolist");
        var associated_users = (JSONArray) project.get("associated_users");
        String projectName = project.get("name").toString();
        String projectLink = project.get("link").toString();
        String projectStatus = project.get("status").toString();
        var associated_project = new JSONObject();
        associated_project.put("name", projectName);
        associated_project.put("id",id);
        associated_project.put("link", projectLink);
        associated_project.put("status", projectStatus);
        if(associated_todolist!=null){
            var todo = (JSONObject) associated_todolist;
            String todolistId = todo.get("id").toString();
            var todolist = todolistKVS.getValue(todolistId);
            todolist.put("associated_project", associated_project);
            todolistKVS.setValue(todolistId, todolist);
            todolistKVS.writeOutFile(filePath+"/todolists.json");
        }
        if(!associated_users.isEmpty()){
            for(Object obj: associated_users){
                var u = (JSONObject) obj;
                String userId = u.get("id").toString();
                var user = userKVS.getValue(userId);
                var projects = (JSONArray) user.get("associated_projects");
                var associated_projects = new JSONArray();
                for(Object o: projects){
                    var pro = (JSONObject) o;
                    
                    if(id == Integer.valueOf(pro.get("id").toString())){
                        associated_projects.add(associated_project);
                    }
                    else{
                        associated_projects.add(pro);
                    }
                }
                user.put("associated_projects", associated_projects);
                userKVS.setValue(userId, user);
                userKVS.writeOutFile(filePath+"/users.json");
            }
        }
    }

    public static void adjustTodolist(int id, String key, String value) {
        var todolist = todolistKVS.getValue(String.valueOf(id));
        todolist.put(key,value);
        todolistKVS.setValue(String.valueOf(id), todolist);
        todolistKVS.writeOutFile(filePath+"/todolists.json");
        var associated_project = todolist.get("associated_project");
        var associated_tasks = (JSONArray) todolist.get("associated_tasks");
        String todolistName = todolist.get("name").toString();
        String todolistLink = todolist.get("link").toString();
        String todolistStatus = todolist.get("status").toString();
        var associated_todolist = new JSONObject();
        associated_todolist.put("id", id);
        associated_todolist.put("name", todolistName);
        associated_todolist.put("status", todolistStatus);
        associated_todolist.put("link", todolistLink);
        if(associated_project!=null){
            var p = (JSONObject) associated_project;
            String projectId = p.get("id").toString();
            var project = projectKVS.getValue(projectId);
            project.put("associated_todolist", associated_todolist);
            projectKVS.setValue(projectId, project);
            projectKVS.writeOutFile(filePath+"/projectss.json");
        }
        if(!associated_tasks.isEmpty()){
            for(Object obj: associated_tasks){
                var t = (JSONObject) obj;
                String taskId = t.get("id").toString();
                var task = taskKVS.getValue(taskId);
                task.put("associated_todolist", associated_todolist);
                taskKVS.setValue(taskId, task);
                taskKVS.writeOutFile(filePath+"/tasks.json");
            }
        }
    }

    public static void adjustTask(int id, String key, String value) {
        var task = taskKVS.getValue(String.valueOf(id));
        task.put(key,value);
        taskKVS.setValue(String.valueOf(id), task);
        taskKVS.writeOutFile(filePath+"/tasks.json");
        var associated_todolist = task.get("associated_todolist");
        var associated_user = task.get("associated_user");
        String taskName = task.get("name").toString();
        String taskDescription = task.get("description").toString();
        String taskStatus = task.get("status").toString();
        String taskLink = task.get("link").toString();
        var associated_task = new JSONObject();
        associated_task.put("id", id);
        associated_task.put("name", taskName);
        associated_task.put("description", taskDescription);
        associated_task.put("status", taskStatus);
        associated_task.put("link", taskLink);
        if(associated_todolist!=null){
            var todo = (JSONObject) associated_todolist;
            var todolistId = todo.get("id").toString();
            var todolist = todolistKVS.getValue(todolistId);
            var tasks = (JSONArray)todolist.get("associated_tasks");
            var associated_tasks = new JSONArray();
            for (Object obj : tasks) {
                var t = (JSONObject) obj;
                if(Integer.valueOf(t.get("id").toString())==id){
                    associated_tasks.add(associated_task);
                }
                else{
                    associated_tasks.add(t);
                }
            }
            todolist.put("associated_tasks",associated_tasks);
            todolistKVS.setValue(todolistId, todolist);
            todolistKVS.writeOutFile(filePath+"/todolists.json");
        }
        if(associated_user!=null){
            var u = (JSONObject) associated_user;
            var userId = u.get("id").toString();
            var user = todolistKVS.getValue(userId);
            var tasks = (JSONArray)user.get("associated_tasks");
            var associated_tasks = new JSONArray();
            for (Object obj : tasks) {
                var t = (JSONObject) obj;
                if(Integer.valueOf(t.get("id").toString())==id){
                    associated_tasks.add(associated_task);
                }
                else{
                    associated_tasks.add(t);
                }
            }
            user.put("associated_tasks",associated_tasks);
            userKVS.setValue(userId, user);
            userKVS.writeOutFile(filePath+"/users.json");
        }
    }

    public static void adjustUser(int id, String key, String value) {
        var user = userKVS.getValue(String.valueOf(id));
        user.put(key,value);
        userKVS.setValue(String.valueOf(id), user);
        userKVS.writeOutFile(filePath+"/users.json");
        var associated_projects = (JSONArray) user.get("associated_projects");
        var associated_tasks = (JSONArray) user.get("associated_tasks");
        String userName = user.get("name").toString();
        String userLink = user.get("link").toString();
        var associated_user = new JSONObject();
        associated_user.put("id", id);
        associated_user.put("name", userName);
        associated_user.put("link", userLink);
        if(!associated_projects.isEmpty()){
            for(Object obj: associated_projects){
                var p = (JSONObject) obj;
                String projectId = p.get("id").toString();
                var project = projectKVS.getValue(projectId);
                var users = (JSONArray) project.get("associated_users");
                var associated_users = new JSONArray();
                for(Object o: users){
                    var u = (JSONObject) o;
                    if(id == Integer.valueOf(u.get("id").toString())){
                        associated_users.add(associated_user);
                    }
                    else{
                        associated_users.add(u);
                    }
                }
                project.put("associated_users", associated_users);
                projectKVS.setValue(projectId, project);
                projectKVS.writeOutFile(filePath+"/projects.json");
            }
        }
        if(!associated_tasks.isEmpty()){
            for(Object obj: associated_tasks){
                var t = (JSONObject) obj;
                String taskId = t.get("id").toString();
                var task = taskKVS.getValue(taskId);
                var users = (JSONArray) task.get("associated_projects");
                var associated_users = new JSONArray();
                for(Object o: users){
                    var u = (JSONObject) o;
                    if(id == Integer.valueOf(u.get("id").toString())){
                        associated_users.add(associated_user);
                    }
                    else{
                        associated_users.add(u);
                    }
                }
                task.put("associated_users", associated_users);
                taskKVS.setValue(taskId, task);
                taskKVS.writeOutFile(filePath+"/tasks.json");
            }
        }
    }

    public static boolean addProjectUser(int projectId, int userId) {
        boolean shouldLink = true;
        if(projectId>projectKVS.getSize()|| userId>userKVS.getSize()){
            shouldLink = false;
        }
        if(shouldLink){
            var project = projectKVS.getValue(String.valueOf(projectId));
            var user = userKVS.getValue(String.valueOf(userId));
            JSONArray associated_users = (JSONArray) project.get("associated_users");
            JSONArray associated_projects = (JSONArray) user.get("associated_projects");
            if(associated_users.size()!=0){
                for(Object obj: associated_users){
                    var u = (JSONObject) obj;
                    int val = Integer.valueOf(u.get("id").toString());
                    if(val == userId){
                        shouldLink = false;
                        break;
                    }
                }
            }
            if(shouldLink){
                String userName = user.get("name").toString();
                String userLink = user.get("link").toString();
                var addedUser = new JSONObject();
                addedUser.put("id",userId);
                addedUser.put("name", userName);
                addedUser.put("link", userLink);
                associated_users.add(addedUser);
                String projectName = project.get("name").toString();
                String projectLink = project.get("link").toString();
                String projectStatus = project.get("status").toString();
                var addedProject = new JSONObject();
                addedProject.put("id", projectId);
                addedProject.put("name", projectName);
                addedProject.put("status", projectStatus);
                addedProject.put("link", projectLink);
                associated_projects.add(addedProject);
                project.put("associated_users", associated_users);
                projectKVS.setValue(String.valueOf(projectId), project);
                user.put("associated_projects", associated_projects);
                userKVS.setValue(String.valueOf(userId), user);
                projectKVS.writeOutFile(filePath+"/projects.json");
                userKVS.writeOutFile(filePath+"/users.json");
            }
            
        }
        return shouldLink;
    }

    public static boolean addTaskTodolist(int taskId, int todolistId) {
        boolean shouldLink = true;
        if(taskId>taskKVS.getSize()|| todolistId>todolistKVS.getSize()){
            shouldLink = false;
        }
        if(shouldLink) {
            var task =taskKVS.getValue(String.valueOf(taskId));
            var todolist = todolistKVS.getValue(String.valueOf(todolistId));
            var associated_tasks = (JSONArray) todolist.get("associated_tasks");
            var oldAssoc_todolist = task.get("associated_todolist");
            var associated_task = new JSONObject();
            associated_task.put("id", taskId);
            associated_task.put("name", task.get("name").toString());
            associated_task.put("description", task.get("description").toString());
            associated_task.put("status", task.get("status").toString());
            associated_task.put("link", task.get("link").toString());
            var associated_todolist = new JSONObject();
            associated_todolist.put("id",todolistId);
            associated_todolist.put("name",todolist.get("name").toString());
            associated_todolist.put("status",todolist.get("status").toString());
            associated_todolist.put("link",todolist.get("link").toString());
            if(oldAssoc_todolist!=null){
                var oldAssociated_todolist = (JSONObject) oldAssoc_todolist;
                if(Integer.valueOf(oldAssociated_todolist.get("id").toString()) == todolistId){
                    shouldLink = false;
                }
                if(shouldLink){
                    String oldTodolistId = oldAssociated_todolist.get("id").toString();
                    var oldTodolist = todolistKVS.getValue(oldTodolistId);
                    var oldAssociated_tasks = (JSONArray) oldTodolist.get("associated_tasks");
                    var newAssoc_tasks = new JSONArray();
                    int i = 0;
                    for (Object obj : oldAssociated_tasks) {
                        var t = (JSONObject) obj;
                        int val = Integer.valueOf(t.get("id").toString());
                        oldTodolist.put("associated_tasks", new JSONArray());
                        todolistKVS.setValue(oldTodolistId, oldTodolist);
                        if(val != taskId){
                            var assoc_task = new JSONObject();
                            assoc_task.put("id",val);
                            assoc_task.put("name", t.get("name").toString());
                            assoc_task.put("description", t.get("description").toString());
                            assoc_task.put("status", t.get("status").toString());
                            assoc_task.put("link", t.get("link").toString());
                            newAssoc_tasks.add(assoc_task);
                            oldTodolist.put("associated_tasks",newAssoc_tasks);
                            todolistKVS.setValue(oldTodolistId, oldTodolist);
                        }
                        i = i+1;
                    }
                }
            }
            task.put("associated_todolist", associated_todolist);
            taskKVS.setValue(String.valueOf(taskId), task);
            associated_tasks.add(associated_task);
            todolist.put("associated_tasks", associated_tasks);
            todolistKVS.setValue(String.valueOf(todolistId), todolist);
            taskKVS.writeOutFile(filePath+"/tasks.json");
            todolistKVS.writeOutFile(filePath+"/todolists.json");
        }
        return shouldLink;
    }

    public static boolean addUserTask(int userId, int taskId) {
        boolean shouldLink = true;
        if(taskId>taskKVS.getSize()|| userId>userKVS.getSize()){
            shouldLink = false;
        }
        if(shouldLink) {
            var task =taskKVS.getValue(String.valueOf(taskId));
            var user = userKVS.getValue(String.valueOf(userId));
            var associated_tasks = (JSONArray) user.get("associated_tasks");
            var oldAssoc_user = task.get("associated_user");
            var associated_task = new JSONObject();
            associated_task.put("id", taskId);
            associated_task.put("name", task.get("name").toString());
            associated_task.put("description", task.get("description").toString());
            associated_task.put("status", task.get("status").toString());
            associated_task.put("link", task.get("link").toString());
            var associated_user = new JSONObject();
            associated_user.put("id",userId);
            associated_user.put("name",user.get("name").toString());
            associated_user.put("link",user.get("link").toString());
            if(oldAssoc_user!=null){
                var oldAssociated_user = (JSONObject) oldAssoc_user;
                if(Integer.valueOf(oldAssociated_user.get("id").toString()) == userId){
                    shouldLink = false;
                }
                if(shouldLink){
                    String oldUserId = oldAssociated_user.get("id").toString();
                    var oldUser = userKVS.getValue(oldUserId);
                    var oldAssociated_tasks = (JSONArray) oldUser.get("associated_tasks");
                    var newAssoc_tasks = new JSONArray();
                    int i = 0;
                    for (Object obj : oldAssociated_tasks) {
                        var t = (JSONObject) obj;
                        int val = Integer.valueOf(t.get("id").toString());
                        oldUser.put("associated_tasks", new JSONArray());
                        userKVS.setValue(oldUserId, oldUser);
                        if(val != taskId){
                            var assoc_task = new JSONObject();
                            assoc_task.put("id",val);
                            assoc_task.put("name", t.get("name").toString());
                            assoc_task.put("description", t.get("description").toString());
                            assoc_task.put("status", t.get("status").toString());
                            assoc_task.put("link", t.get("link").toString());
                            newAssoc_tasks.add(assoc_task);
                            oldUser.put("associated_tasks",newAssoc_tasks);
                            userKVS.setValue(oldUserId, oldUser);
                        }
                        i = i+1;
                    }
                }
            }
            task.put("associated_user", associated_user);
            taskKVS.setValue(String.valueOf(taskId), task);
            associated_tasks.add(associated_task);
            user.put("associated_tasks", associated_tasks);
            userKVS.setValue(String.valueOf(userId), user);
            taskKVS.writeOutFile(filePath+"/tasks.json");
            userKVS.writeOutFile(filePath+"/users.json");
        }
        return shouldLink;
    }

    public static JSONArray getProjectUsers(int id) {
        var project = projectKVS.getValue(String.valueOf(id));
        var associated_users = (JSONArray) project.get("associated_users");
        return associated_users;
    }

    public static JSONArray getUserTasks(int id) {
        var user = userKVS.getValue(String.valueOf(id));
        var associated_tasks = (JSONArray) user.get("associated_tasks");
        return associated_tasks;
    }

    public static JSONArray getUserProjects(int id) {
        var user = userKVS.getValue(String.valueOf(id));
        var associated_projects = (JSONArray) user.get("associated_projects");
        return associated_projects;
    }

    
    public static JSONObject getTaskUser(int id) {
        var task = taskKVS.getValue(String.valueOf(id));
        var assoc_user =  task.get("associated_user");
        if(assoc_user== null){
            return null;
        }
        var associated_user = (JSONObject) assoc_user;
        return associated_user;
    }
    
    public static JSONObject getTaskTodolist(int id) {
        var task = taskKVS.getValue(String.valueOf(id));
        var assoc_todolist = task.get("associated_todolist");
        if(assoc_todolist==null){
            return null;
        }
        var associated_todolist = (JSONObject) assoc_todolist;
        return associated_todolist;
    }

    public static JSONObject getProjectTodolist(int id) {
        var project = projectKVS.getValue(String.valueOf(id));
        var assoc_todolist = project.get("associated_todolist");
        if(assoc_todolist==null){
            return null;
        }
        var associated_todolist = (JSONObject) assoc_todolist;
        return associated_todolist;
    }

    public static JSONArray getTodolistTasks(int id){
        var todolist = todolistKVS.getValue(String.valueOf(id));
        var associated_tasks = (JSONArray) todolist.get("associated_tasks");
        return associated_tasks;
    }

    public static JSONObject getTodolistProject(int id){
        var todolist = todolistKVS.getValue(String.valueOf(id));
        var assoc_project = todolist.get("associated_project");
        if(assoc_project == null){
            return null;
        }
        var associated_project = (JSONObject) assoc_project;
        return associated_project;
    }
        
    public static boolean removeProjectUser(int projectId, int userId) {
        boolean shouldRemove = true;
        if(projectId>projectKVS.getSize()|| userId>userKVS.getSize()){
            shouldRemove = false;
        }
        if(shouldRemove){
            var project = projectKVS.getValue(String.valueOf(projectId));
            var user = userKVS.getValue(String.valueOf(userId));
            JSONArray associated_users = (JSONArray) project.get("associated_users");
            JSONArray associated_projects = (JSONArray) user.get("associated_projects");
            shouldRemove = false;
            int i = 0;
            for(Object obj: associated_users){
                var u = (JSONObject) obj;
                int val = Integer.valueOf(u.get("id").toString());
                if(val == userId){
                    shouldRemove = true;
                    associated_users.remove(i);
                    project.put("associated_users", associated_users);
                    projectKVS.setValue(String.valueOf(projectId), project);
                    projectKVS.writeOutFile(filePath+"/projects.json");
                    break;
                }
                i = i+1;
            }
            if(shouldRemove){
                i = 0;
                for(Object obj: associated_projects){
                    var p = (JSONObject) obj;
                    int val = Integer.valueOf(p.get("id").toString());
                    if(val == projectId){
                        associated_projects.remove(i);
                        user.put("associated_projects", associated_projects);
                        userKVS.setValue(String.valueOf(userId), user);
                        userKVS.writeOutFile(filePath+"/users.json");
                        break;
                    }
                    i = i+1;
                }
            }
        }

        return shouldRemove;
    }

    public static boolean removeTaskTodolist(int taskId, int todolistId) {
        boolean shouldremove = true;
        if(taskId>taskKVS.getSize()|| todolistId>todolistKVS.getSize()){
            shouldremove = false;
        }
        if(shouldremove){
            var task = taskKVS.getValue(String.valueOf(taskId));
            var associated_todolist = (JSONObject) task.get("associated_todolist");
            if(Integer.valueOf(associated_todolist.get("id").toString())==todolistId){
                task.put("associated_todolist", null);
                taskKVS.setValue(String.valueOf(taskId), task);
                taskKVS.writeOutFile(filePath+"/tasks.json");
                var todolist = todolistKVS.getValue(String.valueOf(todolistId));
                var associated_tasks = (JSONArray) todolist.get("associated_tasks");
                int i = 0;
                for (Object obj : associated_tasks) {
                    var t = (JSONObject) obj;
                    int val = Integer.valueOf(t.get("id").toString());
                    if(val == taskId){
                        associated_tasks.remove(i);
                        todolist.put("associated_tasks", associated_tasks);
                        todolistKVS.setValue(String.valueOf(todolistId), todolist);
                        break;
                    }
                    i = i+1;
                }
                todolistKVS.writeOutFile(filePath+"/todolists.json");
            }
            else{
                shouldremove = false;
            }
        }
        return shouldremove;
    }

    public static boolean removeUserTask(int userId, int taskId) {
        boolean shouldremove = true;
        if(taskId>taskKVS.getSize()|| userId>userKVS.getSize()){
            shouldremove = false;
        }
        if(shouldremove){
            var task = taskKVS.getValue(String.valueOf(taskId));
            var associated_user = (JSONObject) task.get("associated_user");
            if(Integer.valueOf(associated_user.get("id").toString())==userId){
                task.put("associated_user", null);
                taskKVS.setValue(String.valueOf(taskId), task);
                taskKVS.writeOutFile(filePath+"/tasks.json");
                var user = userKVS.getValue(String.valueOf(userId));
                var associated_tasks = (JSONArray) user.get("associated_tasks");
                int i = 0;
                for (Object obj : associated_tasks) {
                    var t = (JSONObject) obj;
                    int val = Integer.valueOf(t.get("id").toString());
                    if(val == taskId){
                        associated_tasks.remove(i);
                        user.put("associated_tasks", associated_tasks);
                        userKVS.setValue(String.valueOf(userId), user);
                        break;
                    }
                    i = i+1;
                }
                userKVS.writeOutFile(filePath+"/users.json");
            }
            else{
                shouldremove = false;
            }
        }
        return shouldremove;
    }

    public static boolean editProjectTodolist(int projectId, int todolistId) {
        boolean shouldLink = true;
        if(projectId>projectKVS.getSize()|| todolistId>todolistKVS.getSize()){
            shouldLink = false;
        }
        if(shouldLink){
            var project = projectKVS.getValue(String.valueOf(projectId));
            var todolist = todolistKVS.getValue(String.valueOf(todolistId));
            var oldP = todolist.get("associated_project");
            var oldT = project.get("associated_todolist");
            if(oldP!=null){
                var oldAssocProject = (JSONObject) oldP;
                var oldProjectId = oldAssocProject.get("id").toString();
                var oldProject = projectKVS.getValue(oldProjectId);
                oldProject.put("associated_todolist", null);
                projectKVS.setValue(oldProjectId, oldProject);
            }
            if(oldT != null){
                var oldAssocTodolist = (JSONObject) oldT;
                var oldTodolistId = oldAssocTodolist.get("id").toString();
                var oldTodolist = todolistKVS.getValue(oldTodolistId);
                oldTodolist.put("associated_project", null);
                todolistKVS.setValue(oldTodolistId, oldTodolist);
            }
            String todolistName = todolist.get("name").toString();
            String todolistStatus = todolist.get("status").toString();
            String todolistLink = todolist.get("link").toString();
            String projectName = project.get("name").toString();
            String projectStatus = project.get("status").toString();
            String projectLink = project.get("link").toString();
            var associated_todolist = new JSONObject();
            associated_todolist.put("id",todolistId);
            associated_todolist.put("name",todolistName);
            associated_todolist.put("status",todolistStatus);
            associated_todolist.put("link",todolistLink);
            var associated_project = new JSONObject();
            associated_project.put("id",projectId);
            associated_project.put("name",projectName);
            associated_project.put("status",projectStatus);
            associated_project.put("link",projectLink);
            project.put("associated_todolist", associated_todolist);
            todolist.put("associated_project", associated_project);
            projectKVS.setValue(String.valueOf(projectId), project);
            todolistKVS.setValue(String.valueOf(todolistId), todolist);
            projectKVS.writeOutFile(filePath+"/projects.json");
            todolistKVS.writeOutFile(filePath+"/todolists.json");
        }
        return shouldLink;
    }

    
   
}
