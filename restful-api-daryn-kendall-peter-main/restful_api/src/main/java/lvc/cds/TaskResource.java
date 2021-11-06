package lvc.cds;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("tasks")
public class TaskResource{

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postNewTask(Task t) {
        int id = KVSStorage.addTask(t);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(""+id).build();
        return Response.created(uri).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getTasks() {
        return KVSStorage.getTasks();

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task getTask(@PathParam("id") int id) {
        return KVSStorage.getTask(id);
    }

    @Path("{id}/name")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public void putTaskName(@PathParam("id") int id, String value){
        KVSStorage.adjustTask(id, "name", value);
    }

    @Path("{id}/description")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public void putTaskDescription(@PathParam("id") int id, String value){
        KVSStorage.adjustTask(id, "description", value);
    }

    @Path("{id}/status")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public void putTaskStatus(@PathParam("id") int id, String value){
        KVSStorage.adjustTask(id, "status", value);
    }

    @Path("{id}/associated_user")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void postTaskUser(@PathParam("id") int id, int userId){
        KVSStorage.addUserTask(userId, id);
    }

    @Path("{id}/associated_todolist")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putTaskTodoList(@PathParam("id") int id, int todolistId){
        KVSStorage.addTaskTodolist(id, todolistId);
    }

    @Path("{id}/associated_user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getTaskUser(@PathParam("id") int id){
        return KVSStorage.getTaskUser(id);
    }

    @Path("{id}/associated_todolist")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getTaskTodoList(@PathParam("id") int id){
        return KVSStorage.getTaskTodolist(id);
    }

    
}