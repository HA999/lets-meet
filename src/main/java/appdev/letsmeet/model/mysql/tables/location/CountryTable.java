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
public class CountryTable implements MySQLDAO {
    
    private final String tableName = "country";
    private final String initFile = "country.txt";
    private final String createStatement = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" +
    "  `Code` char(3) NOT NULL DEFAULT '',\n" +
    "  `Name` char(52) NOT NULL DEFAULT '',\n" +
    "  `Continent` enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') NOT NULL DEFAULT 'Asia',\n" +
    "  `Region` char(26) NOT NULL DEFAULT '',\n" +
    "  `SurfaceArea` float(10,2) NOT NULL DEFAULT '0.00',\n" +
    "  `IndepYear` smallint(6) DEFAULT NULL,\n" +
    "  `Population` int(11) NOT NULL DEFAULT '0',\n" +
    "  `LifeExpectancy` float(3,1) DEFAULT NULL,\n" +
    "  `GNP` float(10,2) DEFAULT NULL,\n" +
    "  `GNPOld` float(10,2) DEFAULT NULL,\n" +
    "  `LocalName` char(45) NOT NULL DEFAULT '',\n" +
    "  `GovernmentForm` char(45) NOT NULL DEFAULT '',\n" +
    "  `HeadOfState` char(60) DEFAULT NULL,\n" +
    "  `Capital` int(11) DEFAULT NULL,\n" +
    "  `Code2` char(2) NOT NULL DEFAULT '',\n" +
    "  PRIMARY KEY (`Code`)\n" +
    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";

    public CountryTable(Connection conn, String filePath) {
        copyDataFileToMySQLFileDirectory(conn, filePath, initFile);
        createTable(conn, createStatement);
        insertFromFile(conn, initFile, tableName);
    }
    
    @Override
    public Serializable insert(Connection conn, Serializable bean) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
