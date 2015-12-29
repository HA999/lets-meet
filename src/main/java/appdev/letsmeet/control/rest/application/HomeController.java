/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import appdev.letsmeet.control.utils.jsonBeans.LocationBean;
import appdev.letsmeet.model.LetsMeet;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author HA999
 */

@Path("home")
public class HomeController {
    
    @Context private HttpServletResponse response;
    @Context private HttpServletRequest request;
    @Context private ServletContext servletContext;
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getCategories() {
        //isKnownUser();
        LetsMeet model = new LetsMeet();
        
        return model.getCategoryList();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String locationList(LocationBean bean) {
        return null;
    }
    
    
        
//    @GET
//    public void isKnownUser() {
//        HttpSession session = request.getSession();
//        
//        try {
//            String id = (String) session.getAttribute("user_id");
//            if (id != null) {
//                LetsMeet model = (LetsMeet) session.getAttribute("model");
//                response.sendRedirect("/login/" + model.getUserName(id));
//            }
//            else {
//                response.sendRedirect(request.getRequestURI().replace("home", "signup"));
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
        
}
