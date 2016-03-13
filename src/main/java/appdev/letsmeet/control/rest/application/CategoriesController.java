/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import appdev.letsmeet.model.LetsMeet;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author HA999
 */
@Path("/categories")
public class CategoriesController {
    
    private final LetsMeet model = LetsMeet.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() {
        List<String> categories = model.getCategoryList();
        return Response.ok(categories).build();
    }
    
    @GET
    @Path("/subcategories/{category}") 
    public Response getSubCategories(@PathParam("category") 
            String category) {
        category = category.trim();
        List<String> subCategories = model.getSubCategoriesInCategory(category);
        
        return Response.ok(subCategories).build();
    }
    
}
