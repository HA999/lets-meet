/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import appdev.letsmeet.control.utils.PasswordService;
import appdev.letsmeet.control.utils.jsonBeans.LocationBean;
import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.model.LetsMeet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author HA999
 */

@Path("home")
public class HomeController { 
    private static final int maxLoginAttempts = 3;
    private HttpSession session; 
    private int loginAttempts;
    private final LetsMeet model = LetsMeet.getInstance();
    
    @Context private HttpServletRequest request;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getCategories() {
        //isKnownUser()
        
        return model.getCategoryList();
    }
    
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(){
        session = request.getSession();
//        if(request.getParameter("logout") != null)
        session.invalidate();
        //delete from the logged in users table in REDIS
        return Response.accepted().build();
//        }
    }
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginUserBean bean){
        session = request.getSession();
        if(session.getAttribute("loginAttempts") == null){
            loginAttempts = 0;
        }
        if(loginAttempts > maxLoginAttempts){
            return prepareMassegeResponse("The number of login attempts exceeded the limit", 401);
            //ridirect to other page???
        }
        else{
            String encryptedPassword = PasswordService.encrypt(bean.password);
            LoginUserBean user = model.authenticateUser(bean.password, encryptedPassword);
            
            //the user exists
            if(user != null){
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("user", user);
                return Response.accepted().build();
                //ridirect to home page of a user?
                //insert to the logged in users table in REDIS

            }
            // user doesn't exist
            else{
                session.setAttribute("loginAttempts", loginAttempts++);
                return prepareMassegeResponse("Error: Unrecognized Username or Password", 401);                   
                //ridirect back to home page?
            }
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String locationList(LocationBean bean) {
        return null;
    }
    
    private Response prepareMassegeResponse(String message, int code){
        return Response.status(code).entity(message).build();
    }
}
