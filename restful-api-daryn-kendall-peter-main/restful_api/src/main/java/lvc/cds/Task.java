package lvc.cds;

import java.util.ArrayList;

import org.json.simple.JSONObject;

public class Task{
    private int id;
    private String name;
    private String description;
    private String status;
    private String link;
    private JSONObject associated_todolist;
    private JSONObject associated_user;

    public Task(){
        this.id = -1;
        this.name = "";
        this.description = "";
        this.link = "";
        this.status = "not started";
        this.associated_todolist = null;
        this.associated_user = null;
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
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public JSONObject getAssociatedTodoList() {
        return associated_todolist;
    }
    public void setAssociatedTodoList(JSONObject associated_todo) {
        this.associated_todolist = associated_todo;
    }

    public JSONObject getAssociatedUser() {
        return associated_user;
    }

    public void setAssociatedUser(JSONObject associated_user) {
        this.associated_user = associated_user;
    }

    public JSONObject toJSON() {
        var ret = new JSONObject();
        ret.put("id", this.id);
        ret.put("name", this.name);
        ret.put("link", this.link);
        ret.put("status", this.status);
        ret.put("description", this.description);
        ret.put("associated_user",associated_user);
        ret.put("associated_project", associated_todolist);
        return ret;
    }


}