/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HA999
 */
public interface MySQLDAO {
    
    default void createTable(Connection conn , String statement) {
        try  {
            PreparedStatement pstmt = conn.prepareStatement(statement);
            pstmt.executeUpdate(statement);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    default void defineIndexes(Connection conn, String index) {
        try  {
            PreparedStatement pstmt = conn.prepareStatement(index);
            pstmt.executeUpdate(index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Serializable insert(Connection conn, Serializable bean) throws SQLException;
    
    default public void insertFromFile(Connection conn, String fileName, String tableName) {
        PreparedStatement pstmt1;
        PreparedStatement pstmt2;
        
        try {
            pstmt1 = conn.prepareStatement("SHOW VARIABLES LIKE 'secure_file_priv'");
            ResultSet rs = pstmt1.executeQuery();
            rs.next();
            String privFilePath = rs.getNString(2);
            
            String statement = "load data local infile '" + privFilePath + fileName + "' into table " + tableName;
            statement = statement.replace('\\', '/');//Linux???
            
            pstmt2 = conn.prepareStatement(statement);
            pstmt2.executeUpdate();
        }catch (SQLException ex) {
            //TODO!!
            System.out.println(ex);
        }
    }
    
    default public void copyDataFileToMySQLFileDirectory (Connection conn, String filePath, String fileName) {
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement("SHOW VARIABLES LIKE 'secure_file_priv'");
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            String privFilePath = rs.getNString(2);
            
            copyFile(filePath + fileName, privFilePath +fileName);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(MySQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    default void copyFile(String sourcePath, String destPath) throws IOException {
        File source = new File(sourcePath);
        File dest = new File(destPath);
        
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
    
//    default void enableForeignKeyChecks(Connection conn) {
//        try  {
//            PreparedStatement pstmt = conn.prepareStatement("SET foreign_key_checks = 1");
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    
//    default void disableForeignKeyChecks(Connection conn) {
//        try  {
//            PreparedStatement pstmt = conn.prepareStatement("SET foreign_key_checks = 0");
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
