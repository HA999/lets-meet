/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import appdev.letsmeet.control.utils.dataObjects.RegistrationInfo;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Owner
 */
@Path("signup")
public class RegistrationController {
    
    @Context private HttpServletResponse response;
    @Context private HttpServletRequest request;
    @Context private ServletContext servletContext;
    
    @POST
    @Path("registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String storeUserInfo() throws ServletException, IOException {
        
        RegistrationInfo info = new RegistrationInfo(request.getParameter("info"));
        
        return info.toJson();
    }
    
    @GET
    @Path("hello/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(@PathParam("name") String name) throws ServletException, IOException{
        StringBuilder stringBuilder = new StringBuilder("Hello ");
        stringBuilder.append(name).append("!");

        return stringBuilder.toString();
    }
}
