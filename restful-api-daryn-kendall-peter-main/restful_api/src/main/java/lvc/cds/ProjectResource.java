package lvc.cds;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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

@Path("projects")
public class ProjectResource{

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getProjects() {
        return KVSStorage.getProjects();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postNewProject(Project p) {
        int id = KVSStorage.addProject(p);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(""+id).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Project getProject(@PathParam("id") int id) {
        return KVSStorage.getProject(id);
    }

    @Path("{id}/name")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeProjectName(@PathParam("id") int id, String value){
        KVSStorage.adjustProject(id,"name", value);
    }

    @Path("{id}/status")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeProjectStatus(@PathParam("id") int id, String value){
        KVSStorage.adjustProject(id,"status", value);
    }

    
    @Path("{id}/associated_users")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getProjectUsers(@PathParam("id") int id){
        return KVSStorage.getProjectUsers(id);
    }

    @Path("{id}/associated_users")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProjectUser(@PathParam("id") int id, int userId){
        boolean added = KVSStorage.addProjectUser(id, userId);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.build();
        if(added){
            return Response.created(uri).build();
        }
        else{
            return Response.status(405).build();
        }
    }  

    @Path("{id}/associated_todolist")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getProjectTodoList(@PathParam("id") int id){
        return KVSStorage.getProjectTodolist(id);
    }

    @Path("{id}/associated_todolist")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putProjectTodoList(@PathParam("id") int id, int todolistId){
        boolean edited = KVSStorage.editProjectTodolist(id, todolistId);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.build();
        if(edited){
            return Response.created(uri).build();
        }
        else{
            return Response.status(405).build();
        }
    }

    @Path("{id}/associated_users/{userId}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProjectUser(@PathParam("id") int id, @PathParam("userId") int userId){
        boolean removed = KVSStorage.removeProjectUser(id, userId);
        if(removed){
            return Response.status(204).build();
        }
        else{
            return Response.status(405).build();
        }
        
    }

}
