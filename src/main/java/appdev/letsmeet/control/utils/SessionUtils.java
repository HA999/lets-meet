/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.utils;

import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import appdev.letsmeet.model.LetsMeet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leppa
 */
public class SessionUtils {
        
    public static Boolean isLoggedInUser(LoginUserBean user, LetsMeet model) {
        if (user != null) {
            return model.isLoggedInUser(user);
        }
        else {
            return false;
        }
    }
    
    public static LoginUserBean getUserFromSession(HttpSession session) {
        //HttpSession session = request.getSession(true);
        if (session != null) {
            return (LoginUserBean) session.getAttribute("user");
        }
        else {
            return null;
        }
    }
}
