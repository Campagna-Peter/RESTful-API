package lvc.cds;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class User {
    private int id;
    private String name;
    private String link;
    private JSONArray associated_projects;
    private JSONArray associated_tasks;
    
    public User() {
        this.id = -1;
        this.name = "";
        this.setLink("");
        this.associated_projects = new JSONArray();
        this.associated_tasks = new JSONArray();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public JSONArray getAssociatedTasks() {
        return associated_tasks;
    }

    public void setAssociatedTasks(JSONArray associated_tasks) {
        this.associated_tasks = associated_tasks;
    }

    public JSONArray getAssociatedProjects() {
        return associated_projects;
    }

    public void setAssociatedProjects(JSONArray associated_projects) {
        this.associated_projects = associated_projects;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public User setName(String name) {
        this.name = name;
        return this;
    }
    public User setId(int id) {
        this.id = id;
        return this;
    }

    public JSONObject toJSON() {
        var ret = new JSONObject();
        ret.put("id", id);
        ret.put("name", name);
        ret.put("link", link);
        ret.put("associated_projects", associated_projects);
        ret.put("associated_tasks", associated_tasks);
        return ret;
    }

    
    
}
