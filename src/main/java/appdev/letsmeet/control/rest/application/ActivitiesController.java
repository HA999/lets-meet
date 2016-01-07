/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import appdev.letsmeet.control.utils.jsonBeans.ActivityBean;
import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.model.LetsMeet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
        //need to call this GET on load of the activities page
        //check if the userneme exists in the Redis loggedin users and is in user session
        //get the LoginUserBean from the session and search in MySQL activities table for all
        //the activities of the user by the user_id
        //return all the activities of the logged in user to show in the browser (on page)
        LoginUserBean user = getUserFromSession();
        if (isLoggedInUser(user)) {
            return getUserActivities(user);
        }
        else {
            return null;
        }
    }
    
    @POST
    @Path("/new") //path for creating new activity
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewActivity(@PathParam("username") String username, ActivityBean bean){
        LoginUserBean user = this.getUserFromSession();
        
        if(isLoggedInUser(user)){
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
    public ActivityBean updateActivity(@PathParam("actid") int actId, ActivityBean bean){
        //update in Redis and SQL the specific activity
        //if successful ridirect back to "{username}/activities" url
        return null;
    }
    
    @POST
    @Path("{actid}/delete")
    public Response deleteActivity(@PathParam("actid") int actId){
        
        //search the actid in SQL and Redis and delete it!
        //Send updated list of activities???
        //if successful ridirect back to "{username}/activities" url
        return null;
    }
    
    private Boolean isLoggedInUser(LoginUserBean user) {
        if (user != null) {
            return model.isLoggedInUser(user);
        }
        else {
            return false;
        }
    }
    
    private LoginUserBean getUserFromSession() {
        HttpSession session = request.getSession(true);
        if (session != null) {
            return (LoginUserBean) session.getAttribute("user");
        }
        else {
            return null;
        }
    }
    
    private List<ActivityBean> getUserActivities(LoginUserBean user) {
        return model.getUserActivities(user);
    }
    
//    @GET
//    @Path("{actid}/requests")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<activityRequsetBean> getRequests(@PathParam("actid") int actId){
//        //gets all the activity requests for the specific activity
//        return null;
//    }
    
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

    
}

//{
//	"name": "fun",
//	"subCatId": "2",
//	"dateTime": "2015-02-11",
//	"country": "JPN",
//	"city": "Tokyo",
//	"about": "bla",
//	"photo": "blaaa"
//}

