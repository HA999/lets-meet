/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import java.io.Serializable;
import java.sql.Connection;

/**
 *
 * @author HA999
 */
public class ActivityTypeSignUpTable implements MySQLDAO{

    private final String createString = 
        "CREATE TABLE IF NOT EXISTS " +
        "Activity_Signup" +
        "(CAT_ID int NOT NULL, " +
        "SUB_CAT_ID int NOT NULL, " +
        "USER_ID int NOT NULL, " +
        "COUNTRY_CODE char(3), " +
        "CITY_CODE char(20)"/////KEYS!!!!!!!!!!!
            + ")";
    
    public ActivityTypeSignUpTable(Connection conn) {
        createTable(conn, createString);
        
    }
    
    @Override
    public Serializable insert(Connection conn, Serializable bean) {
//        ActivityTypeSignUpBean atsBean = (ActivityTypeSignUpBean) bean;
//        PreparedStatement pstmt;
//        
//            try {
//                validateInput(atsBean);
//                pstmt = conn.prepareStatement("INSERT INTO Activity_Signup "
//                        + "(CAT_ID, "
//                        + "SUB_CAT_ID, "
//                        + "USER_ID, "
//                        + "COUNTRY_CODE, "
//                        + "CITY_CODE) VALUES("
//                        + "'" + atsBean.catId + "', "
//                        + "'" + atsBean.subCatId + "', "
//                        + "'" + atsBean.userId + "', "
//                        + "'" + atsBean.country + "', "
//                        + "'" + atsBean.city + "')");
//
//                pstmt.executeUpdate();
//            }catch (SQLException ex) {
//                System.out.println(ex);
//            }
//            }finally {
//                if (conn != null) conn.close();
//            }
        return bean;
    }
    
}
