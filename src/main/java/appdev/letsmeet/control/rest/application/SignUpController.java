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
import com.sun.jersey.api.view.Viewable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author Owner
 */
@Path("signup")
public class SignUpController {
    
    @Context private HttpServletRequest request;
    private LetsMeet model = LetsMeet.getInstance();
    
    @GET
    public Viewable signupPage() throws ServletException, IOException, 
            URISyntaxException {
        
        return new Viewable("/signup.html");
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response storeUserInfo(RegistrationBean bean) 
            throws ServletException, IOException {
        
        bean.password = PasswordService.encrypt(bean.password);
        //need to validate user input????
        LoginUserBean user = model.addUser(bean);
        
        if(user != null){
            HttpSession session = request.getSession(true);
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("user", user);
            try {
                return Response.seeOther(new URI("/" + user.username)).build();
            } catch (URISyntaxException ex) {
                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //insert into the logged in users table in REDIS
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
