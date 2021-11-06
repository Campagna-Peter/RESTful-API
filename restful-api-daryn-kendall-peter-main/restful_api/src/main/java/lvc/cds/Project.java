package lvc.cds;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Project{
    private int id;
    private String name;
    private String status;
    private String link;
    private JSONObject associated_todolist;
    private JSONArray associated_users;
    

    public Project(){
        this.id = -1;
        this.name = "";
        this.status = "not started";
        this.link = "";
        this.associated_todolist = null;
        this.associated_users = new JSONArray();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public Project setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public JSONObject getAssociatedTodoList() {
        return associated_todolist;
    }
    public void setAssociatedTodoList(JSONObject associated_todolist) {
        this.associated_todolist = associated_todolist;
    }

    public JSONArray getAssociatedUsers() {
        return associated_users;
    }
    public void setAssociatedUsers(JSONArray associated_users) {
        this.associated_users = associated_users;
    }

    public JSONObject toJSON() {
        var ret = new JSONObject();
        ret.put("id", this.id);
        ret.put("name", this.name);
        ret.put("associated_users", associated_users);
        ret.put("associated_todolist", associated_todolist);
        ret.put("link", link);
        ret.put("status", status);
        return ret;

    }



}