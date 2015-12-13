/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.utils.dataObjects;

import java.io.Serializable;

/**
 *
 * @author Owner
 */
public interface JsonDataObject {
    
    public void fromJson(String json);
    public Serializable getObject();   
    public String toJson();
}
