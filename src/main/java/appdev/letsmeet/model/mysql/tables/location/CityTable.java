/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables.location;

import appdev.letsmeet.model.mysql.tables.MySQLDAO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author HA999
 */
public class CityTable implements MySQLDAO {

    private final String tableName = "city";
    private final String initFile = "city.txt";
    private final String createStatement = "CREATE TABLE " + tableName  + " (\n" +
    "  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
    "  `Name` char(35) NOT NULL DEFAULT '',\n" +
    "  `CountryCode` char(3) NOT NULL DEFAULT '',\n" +
    "  `District` char(20) NOT NULL DEFAULT '',\n" +
    "  `Population` int(11) NOT NULL DEFAULT '0',\n" +
    "  PRIMARY KEY (`ID`),\n" +
    "  KEY `CountryCode` (`CountryCode`),\n" +
    "  CONSTRAINT `city_ibfk_1` FOREIGN KEY (`CountryCode`) REFERENCES `country` (`Code`)\n" +
    ") ENGINE=InnoDB AUTO_INCREMENT=4080 DEFAULT CHARSET=latin1;";
    
    private final String indexString = "CREATE UNIQUE INDEX city_index ON "
            + tableName + "(Name)";
    
    public CityTable(Connection conn, String filePath) {
        copyDataFileToMySQLFileDirectory(conn, filePath, initFile);
        createTable(conn, createStatement);
        defineIndexes(conn, indexString);
        insertFromFile(conn, initFile, tableName);
    }
    
    @Override
    public Serializable insert(Connection conn, Serializable bean) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
