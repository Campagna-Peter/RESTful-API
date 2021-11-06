package lvc.cds;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TodoList{
    private int id;
    private String name;
    private String link;
    private JSONArray associated_tasks;
    private JSONObject associated_project;
    private String status;

    public TodoList(){
        this.id = -1;
        this.name = "";
        this.setLink("");
        this.associated_tasks = new JSONArray();
        this.associated_project = null;
        this.status = "not_started";
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }
    public TodoList setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public JSONArray getAssociatedTasks() {
        return associated_tasks;
    }
    public void setAssociatedTasks(JSONArray associated_tasks) {
        this.associated_tasks = associated_tasks;
    }

    public JSONObject getAssociatedProject() {
        return associated_project;
    }
    public void setAssociatedProject(JSONObject associated_project) {
        this.associated_project= associated_project;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public JSONObject toJSON() {
        var ret = new JSONObject();
        ret.put("id", this.id);
        ret.put("name", this.name);
        ret.put("link", this.link);
        ret.put("status", this.status);
        ret.put("associated_tasks",associated_tasks);
        ret.put("associated_project",associated_project);
        return ret;
    }
  
}