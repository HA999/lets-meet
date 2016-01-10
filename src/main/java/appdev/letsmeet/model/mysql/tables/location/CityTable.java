/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.mysql.tables.location;

import appdev.letsmeet.model.mysql.tables.mysqldao.MySQLDAO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HANAN&OLYA
 */
public class CityTable implements MySQLDAO {

    private final String tableName = "city";
    private final String initFile = "city.txt";
    private final String name_col = "Name";
    private final String countryCode_col = "CountryCode";
    private final String countryTableName = "country";
    private final String countryTableCountryCode_col = "Code";
    private final String createStatement = "CREATE TABLE " + tableName  + " (\n" +
    "  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
    "  `Name` char(35) NOT NULL DEFAULT '',\n" +
    "  `CountryCode` char(3) NOT NULL DEFAULT '',\n" +
    "  `District` char(20) NOT NULL DEFAULT '',\n" +
    "  `Population` int(11) NOT NULL DEFAULT '0',\n" +
    "  PRIMARY KEY (`ID`),\n" +
    "  KEY `CountryCode` (`CountryCode`),\n" +
    "  CONSTRAINT `city_ibfk_1` FOREIGN KEY (`CountryCode`) REFERENCES " + countryTableName + " (`" + countryTableCountryCode_col + "`)\n" +
    ") ENGINE=InnoDB AUTO_INCREMENT=4080 DEFAULT CHARSET=latin1;";
    
    private final String indexString = "CREATE INDEX city_index ON "
            + tableName + "(" + name_col + ")";
    
    public CityTable(Connection conn, String filePath) {
        copyDataFileToMySQLFileDirectory(conn, filePath, initFile);
        createTable(conn, createStatement);
        defineIndexes(conn, indexString);
        insertFromFile(conn, initFile, tableName);
    }
    
    @Override
    public Serializable insert(Connection conn, Serializable bean) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public List<String> getCitiesInCountryList(Connection conn, String country) throws SQLException {
        List<String> resultList = new ArrayList<>();
        String statement = "SELECT " + countryTableCountryCode_col + " FROM " + countryTableName + " WHERE " + name_col + " = " + country;
        ResultSet rs1 = executeQueryStatment(conn, statement);
        rs1.next();
        String countryCode = rs1.getString(countryTableCountryCode_col);
        statement = "SELECT " + name_col + " FROM " + tableName + " WHERE " + countryCode_col + " = " + countryCode;
        ResultSet rs2 = executeQueryStatment(conn, statement);
        
        while (rs2.next()) {
            resultList.add(rs2.getString(name_col));
        }
        return resultList;
    }
    
}
