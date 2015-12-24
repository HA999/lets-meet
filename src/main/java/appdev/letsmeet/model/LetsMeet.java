/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model;

import appdev.letsmeet.control.utils.jsonBeans.RegistrationBean;
import appdev.letsmeet.model.mysql.MySQLHandler;

/**
 *
 * @author HA999
 */
public class LetsMeet {
    
    private final MySQLHandler mysqlHandler = new MySQLHandler();
    
    public void addUser(RegistrationBean rbean) {
        mysqlHandler.addUser(rbean);
    }
}
