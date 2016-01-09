/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import appdev.letsmeet.control.utils.SessionUtils;
import appdev.letsmeet.control.utils.jsonBeans.ActivityBean;
import appdev.letsmeet.control.utils.jsonBeans.ActivityRequestBean;
import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.model.LetsMeet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author leppa
 */
@Path("{username}/activities")
public class ActivitiesController {
    
    private final LetsMeet model = LetsMeet.getInstance();
    @Context private HttpServletRequest request;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ActivityBean> getUserActivities(@PathParam("username") String username){
        LoginUserBean user = SessionUtils.getUserFromSession(request.getSession(true));
        if (SessionUtils.isLoggedInUser(user, model)){
            if(user.username.equals(username)){
                return model.getUserActivities(user);
            }
        }
        return null;
    }
    
    @GET
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories(@QueryParam("category") String category,
        @QueryParam("sub") String sub) {
        
        List<String> categories = model.getCategories(category, sub);
        if (categories != null) {
            return Response.ok(categories).build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("/new") //path for creating new activity
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewActivity(@PathParam("username") String username, ActivityBean bean){
        LoginUserBean user = SessionUtils.getUserFromSession(request.getSession(true));
        
        if (SessionUtils.isLoggedInUser(user, model)){
            if(user.username.equals(username)){
                bean.userId = user.user_Id;
                ActivityBean updatedBean = model.addNewActivity(bean);
                if(updatedBean != null){
                    try {
                        //returns the created activity_id if seccessful 
                        return Response.seeOther(new URI("/")).status(201).entity(updatedBean.actId).build();
                    } catch (URISyntaxException ex) {
                        return Response.serverError().build();
                    }
                } 
            }
        }
        return Response.status(400).build();
    }
    
    @POST
    @Path("{actid}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateActivity(@PathParam("actid") String actId,
            @PathParam("username") String username, ActivityBean bean){
        LoginUserBean user = SessionUtils.getUserFromSession(request.getSession(true));
        
        if (SessionUtils.isLoggedInUser(user, model)){
            if(user.username.equals(username)){
                bean.userId = user.user_Id;
                bean.actId = actId;
                Boolean isUpdated = model.updateActivity(bean);
                if(isUpdated){
                    try {
                        return Response.seeOther(new URI("/")).status(201).build();
                    } catch (URISyntaxException ex) {
                        return Response.serverError().build();
                    }
                } 
            }
        }
        return Response.status(400).build();
    }
    
    @POST
    @Path("{actid}/delete")
    public Response deleteActivity(@PathParam("actid") String actId,
            @PathParam("username") String username){
        //they can update time, about, name, location - they can not change the category or sub category
        LoginUserBean user = SessionUtils.getUserFromSession(request.getSession(true));
        if (SessionUtils.isLoggedInUser(user, model)){
            if(user.username.equals(username)){
                Boolean isDeleted = model.deleteActivity(actId);
                if(isDeleted){
                    try {
                        return Response.seeOther(new URI("/")).status(200).build();
                    } catch (URISyntaxException ex) {
                        return Response.serverError().build();
                    }
                } 
            }
        }
        return Response.status(400).build();
    }
    
    @GET
    @Path("{actid}/requests")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequestsByActivityID(@PathParam("actid") String actID, @PathParam("username") String username) {
        LoginUserBean user = SessionUtils.getUserFromSession(request.getSession(true));
        if (SessionUtils.isLoggedInUser(user, model)){
            if(user.username.equals(username)){
                return Response.status(Response.Status.OK).entity(model.getRequestsByActivityID(actID)).build();
            }
        }
        try {
            return Response.seeOther(new URI("/")).status(201).build();
        } catch (URISyntaxException ex) {
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("{actid}/requests")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateActivityRequest(@PathParam("actid") String actID, ActivityRequestBean bean) {
        LoginUserBean user = SessionUtils.getUserFromSession(request.getSession(true));
        if (SessionUtils.isLoggedInUser(user, model) || !actID.equals(bean.actID)) {
            if (model.updateActivityRequest(bean)) {
                return Response.accepted().build();
            }
            else {
                return Response.notModified().build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
    //Post method that accepsts or delcines the request and deletes it???

    //the user can delete one of them 
    //the user can signup for new activity signup
//    @POST
//    @Path("actnotification")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public List<ActivitySignupBean> getActivitySignUp(){
//        //returns the list of activitySignups of the user
//        return null;
//    }
//    

//{
//	"name": "fun",
//	"subCatId": "2",
//	"dateTime": "2015-02-11",
//	"country": "JPN",
//	"city": "Tokyo",
//	"about": "bla",
//	"photo": "blaaa"
//}

