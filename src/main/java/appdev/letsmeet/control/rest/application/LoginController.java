/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import appdev.letsmeet.model.LetsMeet;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author HA999
 */

@Path("home")
public class LoginController {
    
    @Context private HttpServletResponse response;
    @Context private HttpServletRequest request;
    @Context private ServletContext servletContext;
    
    @GET
    public void knownUser() {
        HttpSession session = request.getSession();
        
        try {
            String id = (String) session.getAttribute("user_id");
            if (id != null) {
                LetsMeet model = (LetsMeet) session.getAttribute("model");
                response.sendRedirect("/login/" + model.getUserName(id));
            }
            else {
                response.sendRedirect(request.getRequestURI().replace("home", "signup"));
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @GET
    @Path("login/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String logIn(@PathParam("username") String username) {
        
                
        return null;
        
    }
    
}
