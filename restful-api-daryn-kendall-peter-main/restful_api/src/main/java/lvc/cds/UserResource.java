package lvc.cds;

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

import java.net.URI;
import java.util.ArrayList;
@Path("users")
public class UserResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postNewUser(User u) {
        int size = KVSStorage.addUser(u);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(""+size).build();
        return Response.created(uri).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getUsers() {
        return KVSStorage.getUsers();
    }

    
    @Path("{id}") 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") int id) {
        return KVSStorage.getUser(id);
    }

    @Path("{id}/name")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchUser(@PathParam("id") int id, String value){
          KVSStorage.adjustUser(id,"name", value);
    }
    

    @Path("{id}/associated_tasks") 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getUserTasks(@PathParam("id") int id){
        return KVSStorage.getUserTasks(id);
    }

    @Path("{id}/associated_tasks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserTask(@PathParam("id") int id, int taskId){
        boolean added = KVSStorage.addUserTask(id, taskId);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.build();
        if(added){
            return Response.created(uri).build();
        }
        else{
            return Response.status(405).build();
        }
    }

    @Path("{id}/associated_tasks/{taskId}")
    @DELETE
    public Response removeUserTask(@PathParam("id") int id, @PathParam("taskId") int taskId){
        boolean removed = KVSStorage.removeUserTask(id, taskId);
        if(removed){
            return Response.status(204).build();
        }
        else{
            return Response.status(405).build();
        }
    }

    @Path("{id}/associated_projects")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserProject(@PathParam("id") int id, int projectId){
        boolean added = KVSStorage.addProjectUser(projectId, id);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.build();
        if(added){
            return Response.created(uri).build();
        }
        else{
            return Response.status(405).build();
        }
    }

    @Path("{id}/associated_projects/{projectId}")
    @DELETE
    public Response removeUserProject(@PathParam("id") int id, @PathParam("projectId") int projectId){
        boolean removed = KVSStorage.removeProjectUser(projectId, id);
        if(removed){
            return Response.status(204).build();
        }
        else{
            return Response.status(405).build();
        }
    }

    @Path("{id}/associated_projects")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public  JSONArray getUserProjects(@PathParam("id") int id){
        return KVSStorage.getUserProjects(id);
    }




}   
