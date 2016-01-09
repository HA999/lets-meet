/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import appdev.letsmeet.control.utils.PasswordService;
import appdev.letsmeet.control.utils.jsonBeans.ActivityBean;
import appdev.letsmeet.control.utils.jsonBeans.LocationBean;
import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.model.LetsMeet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
 * @author HA999
 */

@Path("/")
public class HomeController { 
    private static final int maxLoginAttempts = 3;
    private HttpSession session; 
    private int loginAttempts;
    private final LetsMeet model = LetsMeet.getInstance();
    
    @Context private HttpServletRequest request;
    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<String> getCategories() {
//        //isKnownUser()
//        
//        //return model.getCategoryList();
//        return null;
//    }
    
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(){
        
        session = request.getSession();
        LoginUserBean user = (LoginUserBean) session.getAttribute("user");
        if (user != null && model.isLoggedInUser(user)) {
            model.removeLoggedInUser(user);
        }
        session.invalidate();
        try {
            return Response.accepted().location(new URI("/")).build();
        } catch (URISyntaxException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginUserBean bean){
        String errorMessage;
        session = request.getSession();
        
        if(session.getAttribute("loginAttempts") == null){
            loginAttempts = 0;
        }
        if(loginAttempts > maxLoginAttempts){
            errorMessage = "The number of login attempts exceeded the limit";
            try {
                return Response.seeOther(new URI("/")).entity(errorMessage).build();
            } catch (URISyntaxException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            String encryptedPassword = PasswordService.encrypt(bean.password);
            LoginUserBean user = model.authenticateUser(bean.username, encryptedPassword);
            
            //the user exists
            if(user != null){
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("user", user);
                model.addLoggedInUser(user);
                
                return Response.ok(user).build();
            }
            // user doesn't exist
            else{
                session.setAttribute("loginAttempts", loginAttempts++);
                errorMessage = "Error: Unrecognized Username or Password";    
                try {
                    return Response.seeOther(new URI("/")).entity(errorMessage).build();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String locationList(LocationBean bean) {
        return null;
    }
    
    @GET
    @Path("search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchActivities(
            @QueryParam("category") String category,
            @QueryParam("subcategory") String subcategory,
            @QueryParam("city") String city){
        
        return getActivities(category, subcategory, city);
    }
    
    @GET
    @Path("{username}/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchActivities(
            @QueryParam("category") String category,
            @QueryParam("subcategory") String subcategory,
            @QueryParam("city") String city,
            @PathParam("username") String username){
        return getActivities(category, subcategory, city);
    }
    
    @GET
    //@Path("recent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecentAddedActivities(){
        List<ActivityBean> activities =  model.getRecentAddedActivities();
        if(activities != null){
            if(!activities.isEmpty()){
                return Response.ok(activities).build();
            }
            else{
                return Response.noContent().build();
            }
        }
        else{
            return Response.serverError().build();
        }
    }
    
    private Response getActivities(String category, String subcategory, String city){
        List<ActivityBean> activities =  model.searchActivities(category, subcategory, city);
        if(activities != null){
            if(!activities.isEmpty()){
                return Response.ok(activities).build();
            }
            else{
                return Response.noContent().build();
            }
        }
        else{
            return Response.serverError().build();
        }
    }
}
