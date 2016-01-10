/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.rest.application;

import com.sun.jersey.api.core.PackagesResourceConfig;
import javax.ws.rs.ApplicationPath;

/**
 *
 * @author HANAN&OLYA
 */
@ApplicationPath("/")
public class RestApplication extends PackagesResourceConfig {
    
    public RestApplication() {
        super("appdev.letsmeet.control");
    }
     
}
