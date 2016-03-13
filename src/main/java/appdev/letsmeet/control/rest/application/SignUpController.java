/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;


import appdev.letsmeet.control.utils.PasswordService;
import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import appdev.letsmeet.model.LetsMeet;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.ServletException;
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
 * @author HANAN&OLYA
 */
@Path("/signup")
public class SignUpController {
    
    @Context private HttpServletRequest request;
    private final LetsMeet model = LetsMeet.getInstance();
    
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response getSignup() {
//        return Response.ok().build();
//    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response storeUserInfo(RegistrationBean bean) 
            throws ServletException, IOException {
        
        bean.password = PasswordService.encrypt(bean.password);
        LoginUserBean user = model.addUser(bean);
        
        if(user != null){
            HttpSession session = request.getSession(true);
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("user", user);
            model.addLoggedInUser(user);
            return Response.ok().build();
        }
        else{
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    
    @GET
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkUniqueUsername(@PathParam("username") String username) {
        Boolean isUniqueUsername = model.isUniqueUsername(username);
        
        if (isUniqueUsername) {
            return Response.ok().build();
        }
        else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
