/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import appdev.letsmeet.control.utils.jsonBeans.SubCategoryBean;
import appdev.letsmeet.model.LetsMeet;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    public Response getCategories(@QueryParam("category") String category,
        @QueryParam("sub") String sub) {
        if (category != null) {
            List<String> categories = model.getCategories(category, sub);
            if (categories != null) {
                return Response.ok(categories).build();
            }
            else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        else {
            return Response.status(Response.Status.OK).build();
        }
    }
    
    @GET
    @Path("/subcategories") 
    public List<SubCategoryBean> getSubCategories() {
        return model.getSubCategories();
    }
    
}
