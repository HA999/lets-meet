/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.utils.dataObjects;

import com.google.gson.Gson;
import java.io.Serializable;

/**
 *
 * @author Owner
 */
public class RegistrationInfo implements JsonDataObject{
    
    RegistrationInfoObject obj = new RegistrationInfoObject();
    
    public RegistrationInfo(){};
    
    public RegistrationInfo(String json) {
        this.fromJson(json);
    }

    @Override
    public void fromJson(String json) {
        
        System.out.println(json);
        
        Gson gson = new Gson();
        obj = gson.fromJson(json, obj.getClass());
    }

    @Override
    public Serializable getObject() {
        return this.obj;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(obj);        
    }
    
    private class RegistrationInfoObject implements Serializable {
        
        public String userName;
        public String firstName;
        public String lastName;
        public String password;
        public String email;
        public String country;
        public String city;
        public String gender;
        public String dateOfBirth;
        public String phone;
        public String photo;
        public String about;
    }
}
