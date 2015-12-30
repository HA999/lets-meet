/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.categories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author HA999
 */
public class CategoriesHandler {
    
    private static CategoriesHandler instance;
    private static final String categoriesFileName = "\\categories.xml";
    private static Document categoriesFile = null;
    private static String categoryFilePath = null;
    private List<String> categories = new ArrayList<>();
    private HashMap<String, List<String>> subCategories = new HashMap<>();

    public static CategoriesHandler getInstance(String categoryFilePath) {
        if (instance == null) {
            synchronized(CategoriesHandler.class) {
                if (instance == null) {
                    CategoriesHandler.categoryFilePath = categoryFilePath;
                    instance = new CategoriesHandler();
                }
            }
        }
        return instance;
    }
    
    public static CategoriesHandler getInstance() {
        return instance;
    }
    
    public CategoriesHandler() {
        if (categoriesFile == null) {
            try {
                categoriesFile =  new SAXBuilder().build(new 
                File(categoryFilePath + categoriesFileName));
                initCategoryList();
                initSubCategoryHasjMap();
            } catch (JDOMException | IOException ex) {
                Logger.getLogger(CategoriesHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    private void initCategoryList() {
        Iterator i = categoriesFile.getRootElement().getChildren("category")
                .iterator();
        while(i.hasNext()) {
            Element c = (Element)i.next();
            categories.add(c.getChildText("name"));
        }
    }

    private void initSubCategoryHasjMap() {
        List<String> currList = new ArrayList<>();
        Iterator i = categoriesFile.getRootElement().getChildren("category")
                .iterator();
        while(i.hasNext()) {
            Element cat = (Element) i.next();
            Iterator j = cat.getChild("sub-categories").getChildren("sub-category").iterator();
            while(j.hasNext()) {
                Element subCat = (Element) j.next();
                currList.add(subCat.getText());
            }
            subCategories.put(cat.getChildText("name"), currList);
            currList.clear();
        }
    }
    public List<String> getCategories() {
        return categories;
    }
    
    public  HashMap<String, List<String>> getSubCategories() {
        return subCategories;
    }
    
}
