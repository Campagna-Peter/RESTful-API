package lvc.cds;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@Path("todolists")
public class TodoListResource{
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postNewList(TodoList l) {
        int size = KVSStorage.addTodolist(l);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(""+size).build();
        return Response.created(uri).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getTodoLists() {
        return KVSStorage.getTodolists();

    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TodoList getTodoList(@PathParam("id") int id) {
        return KVSStorage.getTodolist(id);
    }

    @Path("{id}/name")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putTodolistName(@PathParam("id") int id, String value) {
         KVSStorage.adjustTodolist(id, "name", value);
    }

    @Path("{id}/status")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putTodolistStatus(@PathParam("id") int id, String value) {
        KVSStorage.adjustTodolist(id, "name", value);
    }
    

    @Path("{id}/associated_tasks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postTodoListTask(@PathParam("id") int id, int taskId){
        boolean added = KVSStorage.addTaskTodolist(taskId, id);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.build();
        if(added){
            return Response.created(uri).build();
        }
        else{
            return Response.status(405).build();
        }
    }

    @Path("{id}/associated_project")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putTodoListProject(@PathParam("id") int id, int projectId){
        boolean edited = KVSStorage.editProjectTodolist(projectId, id);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.build();
        if(edited){
            return Response.created(uri).build();
        }
        else{
            return Response.status(405).build();
        }
    }

    @Path("{id}/associated_tasks/{taskId}")
    @DELETE
    public Response removeTodoListTask(@PathParam("id") int id, @PathParam("taskId") int taskId){
        boolean removed = KVSStorage.removeTaskTodolist(taskId, id);
        if(removed){
            return Response.status(204).build();
        }
        else{
            return Response.status(405).build();
        }
    }

    @Path("{id}/associated_tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getTodoListTasks(@PathParam("id") int id){
        return KVSStorage.getTodolistTasks(id);
    }

    @Path("{id}/associated_project")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getTodoListProject(@PathParam("id") int id){
        return KVSStorage.getTodolistProject(id);
    }
    
}